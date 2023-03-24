package org.ssp;

public interface CommandController {

    void signUp(String login, String password);

    void signIn(String login, String password);

    void start(String login);

    void logout();

    void stepFirst(String login, StepValues step);

    void stepSecond(String login, StepValues step);

    void stepThird(String login, StepValues step);

    ResultValues countResult(String login);
}
