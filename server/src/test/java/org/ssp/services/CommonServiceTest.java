package org.ssp.services;

import org.junit.jupiter.api.Test;
import org.ssp.StepValue;
import org.ssp.security.TokenService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CommonServiceTest {

    @Test
    public void start() {
        String token = "token";
        String login = "login";
        GameService gameService = mock(GameService.class);
        UserService userService = mock(UserService.class);

        TokenService tokenService = mock(TokenService.class);
        when(tokenService.getLoginFromToken(eq(token))).thenReturn(login);

        CommonService commonService = new CommonServiceImpl(gameService, userService, tokenService);

        //WHEN
        commonService.start(token);

        verify(tokenService).validateToken(eq(token));
        verify(userService).getUser(eq(login));
        verify(gameService).createGame(any());
    }

    @Test
    public void step() {
        String token = "token";
        String login = "login";
        StepValue value = StepValue.STONE;
        GameService gameService = mock(GameService.class);
        UserService userService = mock(UserService.class);

        TokenService tokenService = mock(TokenService.class);
        when(tokenService.getLoginFromToken(eq(token))).thenReturn(login);

        CommonService commonService = new CommonServiceImpl(gameService, userService, tokenService);

        //WHEN
        commonService.step(value, token);

        verify(tokenService).validateToken(eq(token));
        verify(userService).getUser(eq(login));
        verify(gameService).step(any(), eq(value));
    }

    @Test
    public void signIn() {
        String login = "login";
        char[] password = "password".toCharArray();
        GameService gameService = mock(GameService.class);
        UserService userService = mock(UserService.class);
        TokenService tokenService = mock(TokenService.class);
        CommonService commonService = new CommonServiceImpl(gameService, userService, tokenService);

        //WHEN
        commonService.signIn(login, password);

        verify(userService).signIn(eq(login), eq(password));
        verify(tokenService).generateToken(eq(login));
    }

    @Test
    public void signUp() {
        String login = "login";
        char[] password = "password".toCharArray();
        GameService gameService = mock(GameService.class);
        UserService userService = mock(UserService.class);
        TokenService tokenService = mock(TokenService.class);
        CommonService commonService = new CommonServiceImpl(gameService, userService, tokenService);

        //WHEN
        commonService.signUp(login, password);

        verify(userService).signUp(eq(login), eq(password));
    }

    @Test
    public void lose() {
        String token = "token";
        String login = "login";
        GameService gameService = mock(GameService.class);
        UserService userService = mock(UserService.class);

        TokenService tokenService = mock(TokenService.class);
        when(tokenService.getLoginFromToken(eq(token))).thenReturn(login);

        CommonService commonService = new CommonServiceImpl(gameService, userService, tokenService);

        //WHEN
        commonService.lose(token);

        verify(tokenService).validateToken(eq(token));
        verify(userService).getUser(eq(login));
        verify(gameService).step(any(), eq(StepValue.LOSE));
    }
}
