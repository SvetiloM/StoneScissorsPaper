package org.ssp.services;

import org.ssp.repository.entity.User;

public interface UserService {

    void signUp(String login, String password);

    void signIn(String login, String password);

    User getUser(String login);
}
