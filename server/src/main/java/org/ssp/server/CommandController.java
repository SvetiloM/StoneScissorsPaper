package org.ssp.server;

import org.ssp.Command;
import org.ssp.ResultValues;

public interface CommandController {

    ResultValues execute(Command command, String token);

    String signIn(String login, String password);

    void signUp(String login, String password);
}
