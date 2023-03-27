package org.ssp;

public interface SspClient {
    void signUp(Command command, String login, char[] password);

    void signIn(Command command, String login, char[] password);

    void start(Command command);

    void step(Command command, StepValue step);

}
