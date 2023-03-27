package org.ssp;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class SspClientImpl implements SspClient {
    private final Client client;
    private String token = "";

    public SspClientImpl() {
        client = new Client();
        client.start();
        Network.register(client);

        client.addListener(new Listener() {
            public void received(Connection c, Object object) {
                if (object instanceof Network.AuthorisationToken) {
                    Network.AuthorisationToken authToken = (Network.AuthorisationToken) object;
                    token = authToken.token;
                } else if (object instanceof Network.Result) {
                    Network.Result result = (Network.Result) object;
                    System.out.println(result.result);
                } else if (object instanceof Network.Time) {
                    Network.Time time = (Network.Time) object;
                    System.out.println("осталось " + time.sec + " секунд");
                } else if (object instanceof Network.LoseStep) {
                    System.out.println("Вы проиграли этот раунд");
                }
            }
        });

        final String host = "localhost";

        new Thread("Connect") {
            public void run() {
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
    public void signUp(Command command, String login, char[] password) {
        Network.Registration reg = new Network.Registration();
        reg.login = login;
        reg.password = password;
        client.sendTCP(reg);
    }

    @Override
    public void signIn(Command command, String login, char[] password) {
        Network.Authorisation auth = new Network.Authorisation();
        auth.login = login;
        auth.password = password;
        client.sendTCP(auth);
    }

    @Override
    public void start(Command command) {
        Network.Step step = new Network.Step();
        step.command = command;
        step.token = token;
        client.sendTCP(step);
    }

    @Override
    public void step(Command command, StepValues step) {
        Network.Step args = new Network.Step();
        args.command = command;
        args.token = token;
        client.sendTCP(args);
    }
}
