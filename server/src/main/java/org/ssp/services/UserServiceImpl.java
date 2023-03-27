package org.ssp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssp.exceptions.SspRepositoryException;
import org.ssp.repository.UserRepository;
import org.ssp.repository.entity.User;

import java.util.Calendar;

import static org.ssp.exceptions.SspException.Ssp_3;
import static org.ssp.exceptions.SspException.Ssp_4;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void signUp(String login, char[] password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(String.valueOf(password).hashCode());
        user.setRegistration_date(Calendar.getInstance().getTime());
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new SspRepositoryException(Ssp_3, ex, login);
        }
    }

    @Override
    public void signIn(String login, char[] password) {
        Integer savedPassword = userRepository.selectByLogin(login)
                .orElseThrow(() -> new SspRepositoryException(Ssp_4, login))
                .getPassword();
        if (String.valueOf(password).hashCode() == savedPassword) {
            userRepository.updateUser(login, Calendar.getInstance().getTime());
        }

    }

    @Override
    public User getUser(String login) {
        return userRepository.selectByLogin(login).orElseThrow(() -> new SspRepositoryException(Ssp_4, login));
    }
}
