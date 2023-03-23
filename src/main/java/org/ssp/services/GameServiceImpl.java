package org.ssp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssp.repository.GameRepository;
import org.ssp.repository.entity.Game;
import org.ssp.repository.entity.User;

import java.util.Random;

@Component
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository repository;

    @Override
    public void createGame(User user) {
        Game lastGame = repository.getLastGame(user);
        if (lastGame == null) {
            Game game = new Game();
            game.setUser(user);
            repository.save(game);
        }
    }

    @Override
    public void stepFirst(User user, StepValues step) {
        Game lastGame = repository.getLastGame(user);
        repository.setStep1(lastGame.getId(), getRandomValue(), step);
    }

    @Override
    public void stepSecond(User user, StepValues step) {
        Game lastGame = repository.getLastGame(user);
        repository.setStep2(lastGame.getId(), getRandomValue(), step);
    }

    @Override
    public void stepThird(User user, StepValues step) {
        Game lastGame = repository.getLastGame(user);
        repository.setStep3(lastGame.getId(), getRandomValue(), step);
    }

    private StepValues getRandomValue() {
        Random random = new Random();
        int i = random.ints(1, StepValues.values().length).findFirst().getAsInt();
        return StepValues.values()[i];
    }

    public void countResult(Game game) {
        byte result = 0;

        result += compareSteps(game.getGame_step_1(), game.getUser_step_1());
        result += compareSteps(game.getGame_step_2(), game.getUser_step_2());
        result += compareSteps(game.getGame_step_3(), game.getUser_step_3());

        if (result < 0) {
            repository.setResult(game.getId(), ResultValues.LOSE);
        } else if (result > 0) {
            repository.setResult(game.getId(), ResultValues.WIN);
        } else {
            repository.setResult(game.getId(), ResultValues.DRAW);
        }
    }

    private byte compareSteps(StepValues game, StepValues user) {
        byte result = 0;
        if (game.equals(StepValues.PAPER) &&
                user.equals(StepValues.STONE)) {
            result--;
        } else if (game.equals(StepValues.SCISSORS) &&
                user.equals(StepValues.PAPER)) {
            result--;
        } else if (game.equals(StepValues.STONE) &&
                user.equals(StepValues.SCISSORS)) {
            result--;
        } else if (game.equals(StepValues.PAPER) &&
                user.equals(StepValues.SCISSORS)) {
            result++;
        } else if (game.equals(StepValues.SCISSORS) &&
                user.equals(StepValues.STONE)) {
            result++;
        } else if (game.equals(StepValues.STONE) &&
                user.equals(StepValues.PAPER)) {
            result++;
        }
        return result;
    }
}
