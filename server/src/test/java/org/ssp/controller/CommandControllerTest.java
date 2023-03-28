package org.ssp.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ssp.Command;
import org.ssp.StepValue;
import org.ssp.exceptions.SspException;
import org.ssp.exceptions.SspRepositoryException;
import org.ssp.services.CommonService;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CommandControllerTest {

    @Test
    public void executeStart() {
        String token = "token";
        CommonService commonService = mock(CommonService.class);
        CommandController commandController = new CommandControllerImpl(commonService);

        commandController.handleCommand(Command.START, token);

        verify(commonService).start(eq(token));
    }

    @ParameterizedTest
    @MethodSource("stepVariants")
    public void execute(Command command, StepValue stepValue) {
        String token = "token";
        CommonService commonService = mock(CommonService.class);
        CommandController commandController = new CommandControllerImpl(commonService);

        commandController.handleCommand(command, token);

        verify(commonService).step(eq(stepValue), eq(token));
    }

    public static Stream<Arguments> stepVariants() {
        return Stream.of(
                Arguments.of(Command.ROCK, StepValue.STONE),
                Arguments.of(Command.SCISSORS, StepValue.SCISSORS),
                Arguments.of(Command.PAPER, StepValue.PAPER)
        );
    }

    @Test
    public void executeFail() {
        String token = "token";
        CommonService commonService = mock(CommonService.class);
        when(commonService.step(any(), any())).thenThrow(new SspRepositoryException(SspException.Ssp_1, 1));
        CommandController commandController = new CommandControllerImpl(commonService);

        Assertions.assertDoesNotThrow(() -> commandController.handleCommand(Command.ROCK, token));
    }

    @Test
    public void signIn() {
        CommonService commonService = mock(CommonService.class);
        CommandController commandController = new CommandControllerImpl(commonService);
        String login = "login";
        char[] password = "password".toCharArray();

        commandController.signIn(login, password);

        verify(commonService).signIn(eq(login), eq(password));
    }

    @Test
    public void signInFail() {
        CommonService commonService = mock(CommonService.class);
        when(commonService.signIn(any(), any())).thenThrow(new SspRepositoryException(SspException.Ssp_1, 1));
        CommandController commandController = new CommandControllerImpl(commonService);
        String login = "login";
        char[] password = "password".toCharArray();

        Assertions.assertDoesNotThrow(() -> commandController.signIn(login, password));
    }

    @Test
    public void signUp() {
        CommonService commonService = mock(CommonService.class);
        CommandController commandController = new CommandControllerImpl(commonService);
        String login = "login";
        char[] password = "password".toCharArray();

        commandController.signUp(login, password);

        verify(commonService).signUp(eq(login), eq(password));
    }

    @Test
    public void signUpFail() {
        CommonService commonService = mock(CommonService.class);
        doAnswer(invocationOnMock -> {
            throw new SspRepositoryException(SspException.Ssp_1, 1);
        }).when(commonService).signUp(any(), any());
        CommandController commandController = new CommandControllerImpl(commonService);
        String login = "login";
        char[] password = "password".toCharArray();

        Assertions.assertDoesNotThrow(() -> commandController.signUp(login, password));
    }

    @Test
    public void lose() {
        CommonService commonService = mock(CommonService.class);
        CommandController commandController = new CommandControllerImpl(commonService);
        String token = "token";

        commandController.lose(token);

        verify(commonService).lose(eq(token));
    }

    @Test
    public void loseFail() {
        CommonService commonService = mock(CommonService.class);
        when(commonService.lose(any())).thenThrow(new SspRepositoryException(SspException.Ssp_1, 1));
        CommandController commandController = new CommandControllerImpl(commonService);
        String token = "token";

        Assertions.assertDoesNotThrow(() -> commandController.lose(token));
    }
}
