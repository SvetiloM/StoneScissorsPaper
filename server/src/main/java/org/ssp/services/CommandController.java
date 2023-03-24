package org.ssp.services;

import org.ssp.Command;

public interface CommandController {

    void execute(Command command, String[] args);
}
