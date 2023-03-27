package org.ssp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ssp.ResultValue;
import org.ssp.StepValues;
import org.ssp.exceptions.SspRepositoryException;
import org.ssp.repository.GameRepository;
import org.ssp.repository.entity.Game;
import org.ssp.repository.entity.User;

import java.util.Optional;
import java.util.Random;

import static org.ssp.exceptions.SspException.Ssp_1;
import static org.ssp.exceptions.SspException.Ssp_2;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository repository;

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
    public Optional<ResultValue> step(User user, StepValues step) {
        Game lastGame = repository.getLastGame(user.getId())
                .orElseThrow(() -> new SspRepositoryException(Ssp_2, user.getLogin()));
        if (lastGame.getGame_step_1() == null) {
            repository.setStep1(lastGame.getId(), getRandomValue(), step);
            return Optional.empty();
        } else if (lastGame.getGame_step_2() == null) {
            repository.setStep2(lastGame.getId(), getRandomValue(), step);
            return Optional.empty();
        } else if (lastGame.getGame_step_3() == null) {
            repository.setStep3(lastGame.getId(), getRandomValue(), step);
            countResult(lastGame.getId());
        }
        return Optional.of(getGameResult(lastGame.getId()));
    }

    @Override
    public ResultValue getGameResult(Integer gameId) {
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
            repository.setResult(game.getId(), ResultValue.LOSE);
        } else if (result > 0) {
            repository.setResult(game.getId(), ResultValue.WIN);
        } else {
            repository.setResult(game.getId(), ResultValue.DRAW);
        }
    }

    private byte compareSteps(StepValues game, StepValues user) {
        if (user.equals(StepValues.LOSE)) return -1;
        if (game.equals(StepValues.PAPER) &&
                user.equals(StepValues.STONE)) {
            return -1;
        } else if (game.equals(StepValues.SCISSORS) &&
                user.equals(StepValues.PAPER)) {
            return -1;
        } else if (game.equals(StepValues.STONE) &&
                user.equals(StepValues.SCISSORS)) {
            return -1;
        } else if (game.equals(StepValues.PAPER) &&
                user.equals(StepValues.SCISSORS)) {
            return 1;
        } else if (game.equals(StepValues.SCISSORS) &&
                user.equals(StepValues.STONE)) {
            return 1;
        } else if (game.equals(StepValues.STONE) &&
                user.equals(StepValues.PAPER)) {
            return 1;
        }
        return 0;
    }
}
