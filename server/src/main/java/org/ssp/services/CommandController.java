package org.ssp.services;

import org.ssp.Command;

public interface CommandController {

    void execute(Command command, String token, String[] args);

    String signIn(String login, String password);
    void signUp(String login, String password);
}
