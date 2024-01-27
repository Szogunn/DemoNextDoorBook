package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsUserByEmailOrLogin(String email, String login);
    Optional<User> findUserByLogin(String login);
}
