package org.ssp.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.ssp.repository.UserRepository;
import org.ssp.repository.entity.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SignServiceTest {

    @Autowired
    private SignService service;

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

        service.SignUp(login, password);

        Assertions.assertNotNull(repository.getPassword(login));
    }

    @Test()
    public void SignUpFail() {
        String login = "loginFail";
        String password = "passwordFail";
        service.SignUp(login, password);

        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> service.SignUp(login, password));
    }

    //todo add fail tests after adding exceptions
    @Test
    public void SignInSuccess() {
        String login = "signInSuccess";
        String password = "signInPassword";
        service.SignUp(login, password);

        service.SignIn(login, password);

        for (User user : repository.findAll()) {
            if (user.getLogin().equals(login)) {
                Assertions.assertNotNull(user.getAuthorisation_date());
                return;
            }
        }
    }
}
