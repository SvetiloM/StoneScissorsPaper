package org.ssp;

import java.util.Arrays;
import java.util.Scanner;

import static org.ssp.Command.DO_NOTHING;
import static org.ssp.Command.LOGOUT;

public class Main {

    private static SspClient client;

    public static void main(String[] args) {
        client = new SspClientImpl();
        Scanner scanner = new Scanner(System.in);

        scanExecuteCommands(scanner);

    }

    private static void scanExecuteCommands(Scanner scanner) {
        Command command;
        do {
            String input = scanner.next();
            String[] strings = input.split("=");

            command = Command.getValueSafely(strings[0]);

            if (!command.equals(DO_NOTHING)) {
                String[] args = Arrays.stream(strings).skip(1).toArray(String[]::new);
                executeCommand(command, args);
            }
        } while (!command.equals(LOGOUT));
    }

    private static void executeCommand(Command command, String... args) {
        switch (command) {
            case SIGNUP -> {
                if (args.length == 2)
                    client.signUp(command, args[0], args[1].toCharArray());
            }
            case SIGNIN -> {
                if (args.length == 2)
                    client.signIn(command, args[0], args[1].toCharArray());
            }
            case START -> {
                client.start(command);
            }
            case ROCK -> {
                client.step(command, StepValues.STONE);
            }
            case PAPER -> {
                client.step(command, StepValues.PAPER);
            }
            case SCISSORS -> {
                client.step(command, StepValues.SCISSORS);
            }
        }
    }

}
