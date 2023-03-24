package org.ssp.services;

import org.ssp.StepValues;
import org.ssp.repository.entity.Game;
import org.ssp.repository.entity.User;

public interface GameService {

    void createGame(User user);

    void step(User userId, StepValues step);

    void countResult(Game game);

}
