package org.ssp.security;

import com.esotericsoftware.kryonet.Connection;
import org.junit.jupiter.api.Test;
import org.ssp.Command;
import org.ssp.Network;
import org.ssp.ResultValue;
import org.ssp.controller.CommandController;
import org.ssp.server.SspServerListener;
import org.ssp.server.TimerManager;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SspServerListenerTest {

    @Test
    public void receiveRegistration() {
        //GIVEN
        CommandController commandController = mock(CommandController.class);
        TimerManager timerManager = mock(TimerManager.class);
        ExecutorService fakeExecutorService = new FakeExecutorService();
        Connection connection = mock(Connection.class);

        String login = "login";
        char[] password = "password".toCharArray();
        Network.Registration registration = new Network.Registration();
        registration.login = login;
        registration.password = password;
        SspServerListener listener = new SspServerListener(commandController,
                timerManager,
                fakeExecutorService);

        //WHEN
        listener.received(connection, registration);

        //THEN
        verify(commandController).signUp(eq(login), eq(password));
    }

    @Test
    public void receiveAuthorisation() {
        //GIVEN
        String login = "login";
        char[] password = "password".toCharArray();
        String token = "token";
        CommandController commandController = mock(CommandController.class);
        when(commandController.signIn(eq(login), eq(password))).thenReturn(token);

        TimerManager timerManager = mock(TimerManager.class);
        ExecutorService fakeExecutorService = new FakeExecutorService();

        Connection connection = mock(Connection.class);
        when(connection.getID()).thenReturn(1);

        Network.Authorisation authorisation = new Network.Authorisation();
        authorisation.login = login;
        authorisation.password = password;
        SspServerListener listener = new SspServerListener(commandController,
                timerManager,
                fakeExecutorService);

        //WHEN
        listener.received(connection, authorisation);

        //THEN
        verify(commandController).signIn(eq(login), eq(password));
        verify(connection).sendTCP(any(Network.AuthorisationToken.class));
        verify(timerManager).stop(eq(1));
    }

    @Test
    public void receiveStepStart() {
        //GIVEN
        String token = "token";

        CommandController commandController = mock(CommandController.class);
        TimerManager timerManager = mock(TimerManager.class);
        ExecutorService fakeExecutorService = new FakeExecutorService();

        Connection connection = mock(Connection.class);
        when(connection.getID()).thenReturn(1);

        Network.Step step = new Network.Step();
        step.command = Command.START;
        step.token = token;

        SspServerListener listener = new SspServerListener(commandController,
                timerManager,
                fakeExecutorService);

        //WHEN
        listener.received(connection, step);

        //THEN
        verify(commandController).handleCommand(eq(Command.START), eq(token));
        verify(timerManager).start(any(), any(), eq(1));
    }

    @Test
    public void receiveStepRock() {
        //GIVEN
        String token = "token";

        CommandController commandController = mock(CommandController.class);
        when(commandController.handleCommand(eq(Command.ROCK), any())).thenReturn(Optional.of(ResultValue.LOSE));

        TimerManager timerManager = mock(TimerManager.class);
        ExecutorService fakeExecutorService = new FakeExecutorService();

        Connection connection = mock(Connection.class);
        when(connection.getID()).thenReturn(1);

        Network.Step step = new Network.Step();
        step.command = Command.ROCK;
        step.token = token;

        SspServerListener listener = new SspServerListener(commandController,
                timerManager,
                fakeExecutorService);

        //WHEN
        listener.received(connection, step);

        //THEN
        verify(commandController).handleCommand(eq(Command.ROCK), eq(token));
        verify(connection).sendTCP(any(Network.Result.class));
        verify(timerManager).stop(eq(1));
    }

    class FakeExecutorService extends ThreadPoolExecutor {

        public FakeExecutorService() {
            super(0, 1, 1, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());
        }

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

}
