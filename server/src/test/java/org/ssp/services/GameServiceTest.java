package org.ssp.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ssp.ResultValue;
import org.ssp.StepValue;
import org.ssp.exceptions.SspRepositoryException;
import org.ssp.repository.GameRepository;
import org.ssp.repository.UserRepository;
import org.ssp.repository.entity.Game;
import org.ssp.repository.entity.User;

import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Stream;

import static org.ssp.exceptions.SspException.Ssp_2;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameServiceTest {

    @Autowired
    private GameService service;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    private User savedUser = new User();

    @BeforeAll
    public void init() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password".hashCode());
        user.setRegistration_date(Calendar.getInstance().getTime());
        savedUser = userRepository.save(user);
    }

    @AfterAll
    public void clean() {
        gameRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void createGame() {
        Optional<Game> previousGame = gameRepository.getLastGame(savedUser.getId());

        service.createGame(savedUser);

        Optional<Game> currentGame = gameRepository.getLastGame(savedUser.getId());

        Assertions.assertTrue(previousGame.isEmpty());
        Assertions.assertTrue(currentGame.isPresent());
    }

    @Test
    @Order(2)
    public void stepFirst() {
        service.step(savedUser, StepValue.PAPER);

        Game lastGame = gameRepository.getLastGame(savedUser.getId()).get();

        Assertions.assertEquals(lastGame.getUser_step_1(), StepValue.PAPER);
        Assertions.assertNotNull(lastGame.getGame_step_1());
    }

    @Test
    @Order(3)
    public void stepSecond() {
        service.step(savedUser, StepValue.SCISSORS);

        Game lastGame = gameRepository.getLastGame(savedUser.getId()).get();

        Assertions.assertEquals(lastGame.getUser_step_2(), StepValue.SCISSORS);
        Assertions.assertNotNull(lastGame.getGame_step_2());
    }

    @Test
    @Order(4)
    public void stepThird() {
        Optional<ResultValue> step = service.step(savedUser, StepValue.STONE);

        Assertions.assertTrue(step.isPresent());
    }

    public static Stream<Arguments> gameVariants() {
        return Stream.of(
                Arguments.of(ResultValue.LOSE,
                        buildGame(StepValue.STONE, StepValue.STONE,
                                StepValue.SCISSORS, StepValue.PAPER,
                                StepValue.PAPER, StepValue.STONE)),
                Arguments.of(ResultValue.WIN,
                        buildGame(StepValue.SCISSORS, StepValue.SCISSORS,
                                StepValue.PAPER, StepValue.SCISSORS,
                                StepValue.STONE, StepValue.PAPER)),
                Arguments.of(ResultValue.DRAW,
                        buildGame(StepValue.PAPER, StepValue.PAPER,
                                StepValue.STONE, StepValue.SCISSORS,
                                StepValue.STONE, StepValue.PAPER))
        );
    }

    @ParameterizedTest
    @Order(5)
    @MethodSource("gameVariants")
    public void setResult(ResultValue expected, Game game) {
        game.setUser(savedUser);
        gameRepository.save(game);

        Optional<ResultValue> result = service.step(savedUser, StepValue.PAPER);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expected, result.get());
    }

    private static Game buildGame(StepValue game1, StepValue user1,
                                  StepValue game2, StepValue user2,
                                  StepValue game3, StepValue user3) {
        Game game = new Game();
        game.setGame_step_1(game1);
        game.setGame_step_2(game2);
        game.setGame_step_3(game3);
        game.setUser_step_1(user1);
        game.setUser_step_2(user2);
        game.setUser_step_3(user3);
        return game;
    }
    @Test
    public void stepFail() {
        Game game = buildGame(StepValue.PAPER, StepValue.PAPER,
                StepValue.STONE, StepValue.SCISSORS,
                StepValue.STONE, StepValue.PAPER);
        game.setResult(ResultValue.WIN);
        game.setUser(savedUser);
        gameRepository.save(game);

        Assertions.assertThrows(SspRepositoryException.class,
                () -> service.step(savedUser, StepValue.PAPER),
                () -> Ssp_2.getMessage().formatted(savedUser));
    }

}
