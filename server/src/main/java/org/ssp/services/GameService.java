package org.ssp.services;

import org.ssp.StepValues;
import org.ssp.repository.entity.Game;
import org.ssp.repository.entity.User;

public interface GameService {

    void createGame(User user);

    void stepFirst(User userId, StepValues step);

    void stepSecond(User userId, StepValues step);

    void stepThird(User userId, StepValues step);

    void countResult(Game game);

}
