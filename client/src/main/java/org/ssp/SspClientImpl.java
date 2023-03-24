package org.ssp;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class SspClientImpl implements SspClient {
    private Client client;

    public SspClientImpl() {
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

    @Override
    public void signUp(Command command, String login, String password) {
        Network.Args args = new Network.Args();
        args.command = command;
        args.args = new String[]{login, password};
        client.sendTCP(args);
    }

    @Override
    public void signIn(Command command, String login, String password) {
        Network.Args args = new Network.Args();
        args.command = command;
        args.args = new String[]{login, password};
        client.sendTCP(args);
    }

    @Override
    public void start(Command command, String login) {
        Network.Args args = new Network.Args();
        args.command = command;
        args.args = new String[]{login};
    }

    @Override
    public void step(Command command, String login, StepValues step) {
        Network.Args args = new Network.Args();
        args.command = command;
        args.args = new String[]{login, step.name()};
    }
}
