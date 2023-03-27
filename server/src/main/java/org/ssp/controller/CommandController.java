package org.ssp.controller;

import org.ssp.Command;
import org.ssp.ResultValues;

public interface CommandController {

    ResultValues execute(Command command, String token);

    String signIn(String login, char[] password);

    void signUp(String login, char[] password);
}
