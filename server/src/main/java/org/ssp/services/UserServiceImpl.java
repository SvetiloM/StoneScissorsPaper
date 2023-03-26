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
    public void signUp(String login, String password) {
        User user = new User();
        user.setLogin(login);
        //todo: save as hash
        user.setPassword(password);
        user.setRegistration_date(Calendar.getInstance().getTime());
        //todo check unique
        System.out.println("WTF???? " + login);
        userRepository.save(user);
    }

    @Override
    public void signIn(String login, String password) {
        String savedPassword = userRepository.getPassword(login);
        //todo add Errors
        if (savedPassword != null) {
            if (password.equals(savedPassword)) {
                userRepository.updateUser(login, Calendar.getInstance().getTime());
                //todo add session
                return;
            }
        }
    }

    @Override
    public User getUser(String login) {
        return userRepository.selectByLogin(login);
    }
}
