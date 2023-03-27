package org.ssp.services;

import org.ssp.repository.entity.User;

public interface UserService {

    void signUp(String login, char[] password);

    void signIn(String login, char[] password);

    User getUser(String login);
}
