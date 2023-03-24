package org.ssp;

import java.util.Arrays;
import java.util.Scanner;

import static org.ssp.Command.DO_NOTHING;
import static org.ssp.Command.LOGOUT;

public class Main {

    private static CommandController commandController = new CommandControllerImpl();

    public static void main(String[] args) {
        new SspClient();
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
                    commandController.signUp(args[0], args[1]);
            }
            case SIGNIN -> {
                if (args.length == 2)
                    commandController.signIn(args[0], args[1]);
            }
            case START -> {
                commandController.start("");
            }
            case ROCK -> {
            }
            case PAPER -> {
            }
            case SCISSORS -> {
            }
        }
    }

}
