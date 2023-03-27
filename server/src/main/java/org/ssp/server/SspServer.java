package org.ssp.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssp.Network;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SspServer {

    private Server server;
    @Autowired
    private CommandController commandController;

    private ExecutorService cachedExecutor = Executors.newCachedThreadPool();

    public SspServer() throws IOException {
        this.server = new Server() {
            protected Connection newConnection() {
                return new SspConnection();
            }
        };

        Network.register(server);

        server.addListener(new Listener() {
            public void received(Connection c, Object object) {
                if (object instanceof Network.Args) {
                    Network.Args args = (Network.Args) object;
                    CompletableFuture.supplyAsync(() -> commandController.execute(args.command, args.login, args.args), cachedExecutor)
                            .thenAcceptAsync(resultValue -> {
                                if (resultValue != null) {
                                    Network.Result result = new Network.Result();
                                    result.result = resultValue;
                                    c.sendTCP(result);
                                }
                            });
                } else if (object instanceof Network.Authorisation) {
                    Network.Authorisation auth = (Network.Authorisation) object;
                    CompletableFuture.supplyAsync(() -> commandController.signIn(auth.login, auth.password), cachedExecutor)
                            .thenAcceptAsync(token -> {
                                Network.AuthorisationToken authToken = new Network.AuthorisationToken();
                                authToken.token = token;
                                c.sendTCP(authToken);
                            });
                } else if (object instanceof Network.Registration) {
                    Network.Registration registration = (Network.Registration) object;
                    cachedExecutor.execute(()->commandController.signUp(registration.login, registration.password));
                }
            }
        });

        server.bind(Network.port);
        server.start();
    }

    static class SspConnection extends Connection {
    }
}
