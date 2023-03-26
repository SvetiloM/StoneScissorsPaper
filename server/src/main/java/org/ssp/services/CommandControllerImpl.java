package org.ssp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssp.Command;
import org.ssp.StepValues;
import org.ssp.repository.entity.User;

@Component
public class CommandControllerImpl implements CommandController {

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @Override
    public void execute(Command command, String login, String[] args) {
        switch (command) {
            case START -> {
                User user = userService.getUser(login);
                gameService.createGame(user);
            }
            case ROCK -> {
                User user = userService.getUser(login);
                gameService.step(user, StepValues.STONE);
            }
            case PAPER -> {
                User user = userService.getUser(login);
                gameService.step(user, StepValues.PAPER);
            }
            case SCISSORS -> {
                User user = userService.getUser(login);
                gameService.step(user, StepValues.SCISSORS);
            }
        }
    }

    @Override
    public String signIn(String login, String password) {
        userService.signIn(login, password);
        //todo create token and return
        return login;
    }

    @Override
    public void signUp(String login, String password) {
        userService.signUp(login, password);
    }

}
