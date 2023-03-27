package org.ssp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssp.repository.UserRepository;
import org.ssp.repository.entity.User;

import java.util.Calendar;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    //todo password as chars
    @Override
    public void signUp(String login, char[] password) {
        User user = new User();
        user.setLogin(login);
        //todo: save as hash
        user.setPassword(String.valueOf(password).hashCode());
        user.setRegistration_date(Calendar.getInstance().getTime());
        //todo add exceptions
        userRepository.save(user);
    }

    @Override
    public void signIn(String login, char[] password) {
        Integer savedPassword = userRepository.getPassword(login);
        //todo add Errors
        if (savedPassword != null) {
            if (String.valueOf(password).hashCode() == savedPassword) {
                userRepository.updateUser(login, Calendar.getInstance().getTime());
            }
        }
    }

    @Override
    public User getUser(String login) {
        return userRepository.selectByLogin(login);
    }
}
