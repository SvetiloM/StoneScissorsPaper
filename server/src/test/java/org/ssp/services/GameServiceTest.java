package org.ssp.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ssp.ResultValues;
import org.ssp.StepValues;
import org.ssp.repository.GameRepository;
import org.ssp.repository.UserRepository;
import org.ssp.repository.entity.Game;
import org.ssp.repository.entity.User;

import java.util.Calendar;
import java.util.stream.Stream;

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
        Game previousGame = gameRepository.getLastGame(savedUser.getId());

        service.createGame(savedUser);

        Game currentGame = gameRepository.getLastGame(savedUser.getId());

        Assertions.assertNull(previousGame);
        Assertions.assertNotNull(currentGame);
    }

    @Test
    @Order(2)
    public void stepFirst() {
        service.step(savedUser, StepValues.PAPER);

        Game lastGame = gameRepository.getLastGame(savedUser.getId());
        Assertions.assertEquals(lastGame.getUser_step_1(), StepValues.PAPER);
        Assertions.assertNotNull(lastGame.getGame_step_1());
    }

    @Test
    @Order(3)
    public void stepSecond() {
        service.step(savedUser, StepValues.SCISSORS);

        Game lastGame = gameRepository.getLastGame(savedUser.getId());
        Assertions.assertEquals(lastGame.getUser_step_2(), StepValues.SCISSORS);
        Assertions.assertNotNull(lastGame.getGame_step_2());
    }

    @Test
    @Order(4)
    public void stepThird() {
        service.step(savedUser, StepValues.STONE);

        Game lastGame = gameRepository.getLastGame(savedUser.getId());
        Assertions.assertEquals(lastGame.getUser_step_3(), StepValues.STONE);
        Assertions.assertNotNull(lastGame.getGame_step_3());
    }

    public static Stream<Arguments> gameVariants() {
        return Stream.of(
                Arguments.of(ResultValues.LOSE,
                        buildGame(StepValues.STONE, StepValues.STONE,
                                StepValues.SCISSORS,StepValues.PAPER,
                                StepValues.PAPER, StepValues.STONE)),
                Arguments.of(ResultValues.WIN,
                        buildGame(StepValues.SCISSORS, StepValues.SCISSORS,
                                StepValues.PAPER, StepValues.SCISSORS,
                                StepValues.STONE, StepValues.PAPER)),
                Arguments.of(ResultValues.DRAW,
                        buildGame(StepValues.PAPER, StepValues.PAPER,
                        StepValues.STONE, StepValues.SCISSORS,
                        StepValues.STONE, StepValues.PAPER))
        );
    }

    @ParameterizedTest
    @Order(5)
    @MethodSource("gameVariants")
    public void setResult(ResultValues expected, Game game) {
        game.setUser(savedUser);
        gameRepository.save(game);

//        service.countResult(game);

//        Assertions.assertEquals(expected, gameRepository.findById(game.getId()).get().getResult());
    }

    private static Game buildGame(StepValues game1, StepValues user1,
                                  StepValues game2, StepValues user2,
                                  StepValues game3, StepValues user3) {
        Game game = new Game();
        game.setGame_step_1(game1);
        game.setGame_step_2(game2);
        game.setGame_step_3(game3);
        game.setUser_step_1(user1);
        game.setUser_step_2(user2);
        game.setUser_step_3(user3);
        return game;
    }
}
