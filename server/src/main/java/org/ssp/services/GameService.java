package org.ssp.services;

import org.ssp.ResultValue;
import org.ssp.StepValues;
import org.ssp.repository.entity.User;

public interface GameService {

    void createGame(User user);

    ResultValue step(User user, StepValues step);

    ResultValue getGameResult(Integer gameId);

}
