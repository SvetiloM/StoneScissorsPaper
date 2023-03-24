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
                    commandController.execute(args.command, args.args);
                }
            }
        });

        server.bind(Network.port);
        server.start();
    }

    static class SspConnection extends Connection {
    }
}
