package org.ssp.controller;

import org.ssp.Command;
import org.ssp.ResultValue;

import java.util.Optional;

public interface CommandController {

    Optional<ResultValue> execute(Command command, String token);

    String signIn(String login, char[] password);

    void signUp(String login, char[] password);

    Optional<ResultValue> lose(String token);
}
