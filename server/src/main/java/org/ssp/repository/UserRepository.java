package org.ssp.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.ssp.repository.entity.User;

import java.util.Date;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.authorisation_date=?2 WHERE u.login=?1")
    void updateUser(String login, Date authorisationDate);

    @Query("SELECT u from User u where u.login=?1")
    Optional<User> selectByLogin(String login);

}
