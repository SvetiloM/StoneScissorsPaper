package org.ssp.services;

import org.ssp.repository.entity.Game;
import org.ssp.repository.entity.User;

public interface GameService {

    void createGame(User user);

    void stepFirst(User userId, StepValues name);

    void stepSecond(User userId, StepValues name);

    void stepThird(User userId, StepValues name);

    void countResult(Game game);

}
