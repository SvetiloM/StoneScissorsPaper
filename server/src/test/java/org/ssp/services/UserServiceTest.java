package org.ssp.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ssp.exceptions.SspRepositoryException;
import org.ssp.repository.GameRepository;
import org.ssp.repository.UserRepository;
import org.ssp.repository.entity.User;

import static org.ssp.exceptions.SspException.Ssp_3;
import static org.ssp.exceptions.SspException.Ssp_4;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private GameRepository gameRepository;

    @BeforeAll
    @AfterAll
    public void clean() {
        gameRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void SignUpSuccess() {
        String login = "loginSuccess";
        char[] password = "passwordSuccess".toCharArray();

        service.signUp(login, password);

        Assertions.assertTrue(repository.selectByLogin(login).isPresent());
    }

    @Test
    public void SignUpFail() {
        String login = "loginFail";
        char[] password = "passwordFail".toCharArray();
        service.signUp(login, password);

        Assertions.assertThrows(SspRepositoryException.class,
                () -> service.signUp(login, password), Ssp_3.getMessage());
    }

    @Test
    public void SignInSuccess() {
        String login = "signInSuccess";
        char[] password = "signInPassword".toCharArray();
        service.signUp(login, password);

        service.signIn(login, password);

        for (User user : repository.findAll()) {
            if (user.getLogin().equals(login)) {
                Assertions.assertNotNull(user.getAuthorisation_date());
                return;
            }
        }
    }

    @Test
    public void getUserSuccess() {
        String login = "getUserSuccess";
        char[] password = "getUserSuccessPassword".toCharArray();
        service.signUp(login, password);

        User user = service.getUser(login);
        Assertions.assertNotNull(user);
    }

    @Test
    public void getUserFail() {
        String login = "getUserFail";

        Assertions.assertThrows(SspRepositoryException.class, () -> service.getUser(login), Ssp_4.getMessage());
    }
}
