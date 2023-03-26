package org.ssp.services;

import org.ssp.ResultValues;
import org.ssp.StepValues;
import org.ssp.repository.entity.User;

public interface GameService {

    void createGame(User user);

    ResultValues step(User user, StepValues step);

    ResultValues getGameResult(Integer gameId);

}
