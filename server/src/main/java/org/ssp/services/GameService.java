package org.ssp.services;

import org.ssp.ResultValue;
import org.ssp.StepValues;
import org.ssp.repository.entity.User;

import java.util.Optional;

public interface GameService {

    void createGame(User user);

    Optional<ResultValue> step(User user, StepValues step);

    ResultValue getGameResult(Integer gameId);

}
