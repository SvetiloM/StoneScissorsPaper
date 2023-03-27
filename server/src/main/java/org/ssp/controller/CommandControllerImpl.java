package org.ssp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.ssp.Command;
import org.ssp.ResultValue;
import org.ssp.StepValues;
import org.ssp.repository.entity.User;
import org.ssp.security.TokenService;
import org.ssp.services.GameService;
import org.ssp.services.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommandControllerImpl implements CommandController {

    private final GameService gameService;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public Optional<ResultValue> execute(Command command, String token) {
        tokenService.validateToken(token);
        switch (command) {
            case START -> {
                User user = userService.getUser(tokenService.getLoginFromToken(token));
                gameService.createGame(user);
            }
            case ROCK -> {
                User user = userService.getUser(tokenService.getLoginFromToken(token));
                return gameService.step(user, StepValues.STONE);
            }
            case PAPER -> {
                User user = userService.getUser(tokenService.getLoginFromToken(token));
                return gameService.step(user, StepValues.PAPER);
            }
            case SCISSORS -> {
                User user = userService.getUser(tokenService.getLoginFromToken(token));
                return gameService.step(user, StepValues.SCISSORS);
            }
        }
        return Optional.empty();
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
        User user = userService.getUser(tokenService.getLoginFromToken(token));
        return gameService.step(user, StepValues.LOSE);
    }

}
