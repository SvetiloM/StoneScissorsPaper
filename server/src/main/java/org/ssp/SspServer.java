package org.ssp;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class SspServer {

    private Server server;

    public SspServer() throws IOException {
        this.server = new Server() {
            protected Connection newConnection () {
                return new SspConnection();
            }
        };

        Network.register(server);

        server.bind(Network.port);

    }

    public void start() {
        server.start();
    }

    static class SspConnection extends Connection {
    }
}
