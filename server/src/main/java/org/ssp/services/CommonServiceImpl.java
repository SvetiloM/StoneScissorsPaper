package org.ssp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.ssp.ResultValue;
import org.ssp.StepValue;
import org.ssp.repository.entity.User;
import org.ssp.security.TokenService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final GameService gameService;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public void start(String token) {
        tokenService.validateToken(token);
        User user = userService.getUser(tokenService.getLoginFromToken(token));
        gameService.createGame(user);
    }

    @Override
    public Optional<ResultValue> step(StepValue value, String token) {
        tokenService.validateToken(token);
        User user = userService.getUser(tokenService.getLoginFromToken(token));
        return gameService.step(user, value);
    }

    @Override
    public String signIn(String login, char[] password) {
        userService.signIn(login, password);
        return tokenService.generateToken(login);
    }

    @Override
    public void signUp(String login, char[] password) {
        userService.signUp(login, password);
    }

    @Override
    public Optional<ResultValue> lose(String token) {
        tokenService.validateToken(token);
        User user = userService.getUser(tokenService.getLoginFromToken(token));
        return gameService.step(user, StepValue.LOSE);
    }

}
