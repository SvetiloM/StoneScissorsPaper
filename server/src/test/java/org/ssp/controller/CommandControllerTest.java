package org.ssp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ssp.Command;
import org.ssp.StepValue;
import org.ssp.security.TokenService;
import org.ssp.services.GameService;
import org.ssp.services.UserService;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CommandControllerTest {

    @Test
    public void executeStart() {
        GameService gameService = mock(GameService.class);
        CommandController commandController = new CommandControllerImpl(
                gameService,
                mock(UserService.class),
                mock(TokenService.class));

        commandController.execute(Command.START, "token");

        verify(gameService).createGame(any());
    }

    @ParameterizedTest
    @MethodSource("stepVariants")
    public void execute(Command command, StepValue stepValue) {
        GameService gameService = mock(GameService.class);
        CommandController commandController = new CommandControllerImpl(
                gameService,
                mock(UserService.class),
                mock(TokenService.class));

        commandController.execute(command, "token");

        verify(gameService).step(any(), eq(stepValue));
    }

    public static Stream<Arguments> stepVariants() {
        return Stream.of(
                Arguments.of(Command.ROCK, StepValue.STONE),
                Arguments.of(Command.SCISSORS, StepValue.SCISSORS),
                Arguments.of(Command.PAPER, StepValue.PAPER)
        );
    }

    @Test
    public void signIn() {
        UserService userService = mock(UserService.class);
        TokenService tokenService = mock(TokenService.class);
        CommandController commandController = new CommandControllerImpl(
                mock(GameService.class),
                userService,
                tokenService);
        String login = "login";
        char[] password = "password".toCharArray();

        commandController.signIn(login, password);

        verify(userService).signIn(eq(login), eq(password));
        verify(tokenService).generateToken(eq(login));
    }

    @Test
    public void signUp() {
        UserService userService = mock(UserService.class);
        CommandController commandController = new CommandControllerImpl(
                mock(GameService.class),
                userService,
                mock(TokenService.class));
        String login = "login";
        char[] password = "password".toCharArray();

        commandController.signUp(login, password);

        verify(userService).signUp(eq(login), eq(password));
    }

    @Test
    public void lose() {
        GameService gameService = mock(GameService.class);
        CommandController commandController = new CommandControllerImpl(
                gameService,
                mock(UserService.class),
                mock(TokenService.class));
        String token = "token";

        commandController.lose(token);

        verify(gameService).step(any(), eq(StepValue.LOSE));
    }
}
