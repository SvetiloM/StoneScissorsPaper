package org.ssp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.ssp.exceptions.SspRepositoryException;
import org.ssp.repository.UserRepository;
import org.ssp.repository.entity.User;

import java.util.Calendar;

import static org.ssp.exceptions.SspException.Ssp_3;
import static org.ssp.exceptions.SspException.Ssp_4;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        Integer savedPassword = getUser(login).getPassword();
        if (String.valueOf(password).hashCode() == savedPassword) {
            userRepository.updateUser(login, Calendar.getInstance().getTime());
        }

    }

    @Override
    public User getUser(String login) {
        return userRepository.selectByLogin(login).orElseThrow(() -> new SspRepositoryException(Ssp_4, login));
    }
}
