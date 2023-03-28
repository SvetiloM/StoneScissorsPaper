package org.ssp.services;

import org.ssp.ResultValue;
import org.ssp.StepValue;

import java.util.Optional;

public interface CommonService {
    void start(String token);

    Optional<ResultValue> step(StepValue value, String token);

    String signIn(String login, char[] password);

    void signUp(String login, char[] password);

    Optional<ResultValue> lose(String token);
}
