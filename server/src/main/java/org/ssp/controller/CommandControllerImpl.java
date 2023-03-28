package org.ssp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.ssp.Command;
import org.ssp.ResultValue;
import org.ssp.StepValue;
import org.ssp.exceptions.SspRepositoryException;
import org.ssp.exceptions.SspTokenException;
import org.ssp.services.CommonService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommandControllerImpl implements CommandController {

    private final CommonService service;

    @Override
    public Optional<ResultValue> handleCommand(Command command, String token) {
        try {
            switch (command) {
                case START -> service.start(token);
                case ROCK -> {
                    return service.step(StepValue.STONE, token);
                }
                case PAPER -> {
                    return service.step(StepValue.PAPER, token);
                }
                case SCISSORS -> {
                    return service.step(StepValue.SCISSORS, token);
                }
            }
        } catch (SspTokenException | SspRepositoryException ex) {
            log.error(ex.getMessage(), ex);
        }
        return Optional.empty();
    }

    @Override
    public String signIn(String login, char[] password) {
        try {
            return service.signIn(login, password);
        } catch (SspTokenException | SspRepositoryException ex) {
            log.error(ex.getMessage(), ex);
        }
        return "";
    }

    @Override
    public void signUp(String login, char[] password) {
        try {
            service.signUp(login, password);
        } catch (SspTokenException | SspRepositoryException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<ResultValue> lose(String token) {
        try {
            return service.lose(token);
        } catch (SspTokenException | SspRepositoryException ex) {
            log.error(ex.getMessage(), ex);
        }
        return Optional.empty();
    }

}
