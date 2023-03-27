package org.ssp.controller;

import org.ssp.Command;
import org.ssp.ResultValue;

public interface CommandController {

    ResultValue execute(Command command, String token);

    String signIn(String login, char[] password);

    void signUp(String login, char[] password);

    ResultValue lose(String token);
}
