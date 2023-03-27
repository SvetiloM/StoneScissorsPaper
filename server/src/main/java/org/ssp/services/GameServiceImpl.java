package org.ssp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssp.ResultValues;
import org.ssp.StepValues;
import org.ssp.exceptions.SspException;
import org.ssp.exceptions.SspRepositoryException;
import org.ssp.repository.GameRepository;
import org.ssp.repository.entity.Game;
import org.ssp.repository.entity.User;

import java.util.Optional;
import java.util.Random;

import static org.ssp.exceptions.SspException.Ssp_2;
import static org.ssp.exceptions.SspException.Ssp_1;

@Component
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository repository;

    @Override
    public void createGame(User user) {
        Optional<Game> optional = repository.getLastGame(user.getId());
        if (optional.isEmpty()) {
            Game game = new Game();
            game.setUser(user);
            repository.save(game);
        }
    }

    @Override
    public ResultValues step(User user, StepValues step) {
        Game lastGame = repository.getLastGame(user.getId())
                .orElseThrow(() -> new SspRepositoryException(Ssp_2, user.getLogin()));
        if (lastGame.getGame_step_1() == null) {
            repository.setStep1(lastGame.getId(), getRandomValue(), step);
            return null;
        } else if (lastGame.getGame_step_2() == null) {
            repository.setStep2(lastGame.getId(), getRandomValue(), step);
            return null;
        } else if (lastGame.getGame_step_3() == null) {
            repository.setStep3(lastGame.getId(), getRandomValue(), step);
            countResult(lastGame.getId());
        }
        return getGameResult(lastGame.getId());
    }

    @Override
    public ResultValues getGameResult(Integer gameId) {
        Game lastGame = repository.findById(gameId)
                .orElseThrow(() -> new SspRepositoryException(Ssp_1, gameId));
        return lastGame.getResult();
    }

    private StepValues getRandomValue() {
        Random random = new Random();
        int i = random.ints(0, StepValues.values().length).findFirst().getAsInt();
        return StepValues.values()[i];
    }

    private void countResult(Integer gameId) {
        Optional<Game> gameOptional = repository.findById(gameId);

        Game game = gameOptional
                .orElseThrow(() -> new SspRepositoryException(Ssp_1, gameId));

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
