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
import java.util.function.Consumer;

@Component
public class SspServer {

    private Server server;
    @Autowired
    private CommandController commandController;

    @Autowired
    private TimerManager timerManager = new TimerManager();

    private final ExecutorService cachedExecutor = Executors.newCachedThreadPool();


    public SspServer() throws IOException {
        this.server = new Server() {
            protected Connection newConnection() {
                return new SspConnection();
            }
        };

        Network.register(server);

        server.addListener(new Listener() {
            public void received(Connection c, Object object) {
                if (object instanceof Network.Step) {
                    Network.Step step = (Network.Step) object;
                    CompletableFuture.supplyAsync(() -> commandController.execute(step.command, step.token), cachedExecutor)
                            .thenAcceptAsync(resultValue -> {
                                if (resultValue != null) {
                                    Network.Result result = new Network.Result();
                                    result.result = resultValue;
                                    c.sendTCP(result);
                                    timerManager.stop(c.getID());
                                }
                            }).thenRun(() -> {
                                Consumer<Byte> sendTime = (sec) -> {
                                    Network.Time time = new Network.Time();
                                    time.sec = sec;
                                    c.sendTCP(time);
                                };
                                timerManager.start(sendTime, c.getID());
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
                    cachedExecutor.execute(() -> commandController.signUp(registration.login, registration.password));
                }
            }

            @Override
            public void disconnected(Connection connection) {
                timerManager.stop(connection.getID());
            }
        });

        server.bind(Network.port);
        server.start();
    }

    static class SspConnection extends Connection {
    }
}
