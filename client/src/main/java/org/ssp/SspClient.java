package org.ssp;

public interface SspClient {
    void signUp(Command command, String login, String password);

    void signIn(Command command, String login, String password);

    void start(Command command);

    void step(Command command, StepValues step);

}
