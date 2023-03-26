package org.ssp;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssp.services.CommandController;

import java.io.IOException;
import java.util.Arrays;

@Component
public class SspServer {

    private Server server;
    @Autowired
    private CommandController commandController;

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
                    commandController.execute(args.command, args.login, args.args);
                } else if (object instanceof Network.Authorisation) {
                    Network.Authorisation auth = (Network.Authorisation) object;
                    System.out.println("запрос на авторизацию");
                    String token = commandController.signIn(auth.login, auth.password);
                    Network.AuthorisationToken authToken = new Network.AuthorisationToken();
                    authToken.token = token;
                    c.sendTCP(authToken);
                    System.out.println("отправлен токен: " + token);
                } else if (object instanceof Network.Registration) {
                    Network.Registration registration = (Network.Registration) object;
                    commandController.signUp(registration.login, registration.password);
                }
            }
        });

        server.bind(Network.port);
        server.start();
    }

    static class SspConnection extends Connection {
    }
}
