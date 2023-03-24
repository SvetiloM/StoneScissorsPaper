package org.ssp;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class SspClient {
    private Client client;

    public SspClient() {
        client = new Client();
        client.start();
        Network.register(client);

        final String host = "localhost";

        new Thread("Connect") {
            public void run () {
                try {
                    client.connect(5000, host, Network.port);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        }.start();
    }
}
