package org.ssp.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssp.Command;
import org.ssp.ResultValues;
import org.ssp.StepValues;
import org.ssp.security.TokenService;
import org.ssp.repository.entity.User;
import org.ssp.services.GameService;
import org.ssp.services.UserService;

@Component
public class CommandControllerImpl implements CommandController {

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Override
    public ResultValues execute(Command command, String login) {
        tokenService.validateToken(login);
        switch (command) {
            case START -> {
                User user = userService.getUser(tokenService.getLoginFromToken(login));
                gameService.createGame(user);
            }
            case ROCK -> {
                User user = userService.getUser(tokenService.getLoginFromToken(login));
                return gameService.step(user, StepValues.STONE);
            }
            case PAPER -> {
                User user = userService.getUser(tokenService.getLoginFromToken(login));
                return gameService.step(user, StepValues.PAPER);
            }
            case SCISSORS -> {
                User user = userService.getUser(tokenService.getLoginFromToken(login));
                return gameService.step(user, StepValues.SCISSORS);
            }
        }
        return null;
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

}
