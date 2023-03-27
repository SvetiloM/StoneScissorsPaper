package org.ssp.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.ssp.Network;
import org.ssp.ResultValue;
import org.ssp.controller.CommandController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class SspServerListener extends Listener {

    private final CommandController commandController;

    private final TimerManager timerManager;

    private final ExecutorService cachedExecutor;

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Network.Step) {
            step((Network.Step) object, connection);
        } else if (object instanceof Network.Authorisation) {
            authorisation((Network.Authorisation) object, connection);
        } else if (object instanceof Network.Registration) {
            registration((Network.Registration) object);
        }
    }

    @Override
    public void disconnected(Connection connection) {
        timerManager.stop(connection.getID());
    }

    private void step(Network.Step step, Connection connection) {
        CompletableFuture.supplyAsync(() -> commandController.execute(step.command, step.token), cachedExecutor)
                .thenAcceptAsync(resultValue -> {
                    if (resultValue != null) {
                        sendResult(resultValue, connection);
                        timerManager.stop(connection.getID());
                    } else {
                        Consumer<Integer> sendTime = (sec) -> {
                            sendTime(sec, connection);
                        };
                        timerManager.start(sendTime, connection.getID());
                    }
                });
    }

    private void sendResult(ResultValue resultValue, Connection connection) {
        Network.Result result = new Network.Result();
        result.result = resultValue;
        connection.sendTCP(result);
    }

    private void sendTime(int sec, Connection connection) {
        Network.Time time = new Network.Time();
        time.sec = sec;
        connection.sendTCP(time);
    }

    private void authorisation(Network.Authorisation auth, Connection connection) {
        CompletableFuture.supplyAsync(() -> commandController.signIn(auth.login, auth.password), cachedExecutor)
                .thenAcceptAsync(token -> {
                    if (token != null) {
                        Network.AuthorisationToken authToken = new Network.AuthorisationToken();
                        authToken.token = token;
                        connection.sendTCP(authToken);

                        timerManager.stop(connection.getID());
                    }
                });
    }

    private void registration(Network.Registration registration) {
        cachedExecutor.execute(() -> commandController.signUp(registration.login, registration.password));
    }
}
