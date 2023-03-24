package org.ssp;

public enum Command {

    DO_NOTHING,
    SIGNUP,
    SIGNIN,
    START,
    LOGOUT,
    ROCK,
    PAPER,
    SCISSORS;

    public static Command getValueSafely(String name) {
        try {
            return Command.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DO_NOTHING;
        }
    }

}
