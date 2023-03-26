package org.ssp.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.ssp.repository.UserRepository;
import org.ssp.repository.entity.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @AfterAll
    public void clean() {
        repository.deleteAll();
    }

    @Test
    public void SignUpSuccess() {
        String login = "loginSuccess";
        String password = "passwordSuccess";

        service.signUp(login, password);

        Assertions.assertNotNull(repository.getPassword(login));
    }

    @Test()
    public void SignUpFail() {
        String login = "loginFail";
        String password = "passwordFail";
        service.signUp(login, password);

        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> service.signUp(login, password));
    }

    //todo add fail tests after adding exceptions
    @Test
    public void SignInSuccess() {
        String login = "signInSuccess";
        String password = "signInPassword";
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
        String password = "getUserPassword";
        service.signUp(login, password);
        service.signIn(login, password);

        User user = service.getUser(login);
        Assertions.assertNotNull(user);
    }
}
