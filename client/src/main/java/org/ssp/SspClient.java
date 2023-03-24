package org.ssp;

public interface SspClient {
    void signUp(Command command, String login, String password);

    void signIn(Command command, String login, String password);

    void start(Command command, String login);

    void step(Command command, String login, StepValues step);

}
