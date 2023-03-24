package org.ssp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssp.Command;
import org.ssp.StepValues;

@Component
public class CommandControllerImpl implements CommandController {

    @Autowired
    private GameService gameService;
    @Autowired
    private SignService signService;

    @Override
    public void execute(Command command, String[] args) {
        switch (command) {
            case SIGNUP -> {
                if (args.length == 2){
                    signService.SignUp(args[0], args[1]);
                }
            }
            case SIGNIN -> {
                if (args.length == 2){
                    signService.SignIn(args[0], args[1]);
                }
            }
            case START -> {
                if (args.length == 2){
//                    gameService.step(args[0], StepValues.valueOf(args[1]));
                }
            }
            case ROCK -> {
            }
            case PAPER -> {
            }
            case SCISSORS -> {
            }
        }
    }

}
