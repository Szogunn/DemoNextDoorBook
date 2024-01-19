package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.User;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<User, Long> {

    boolean existsOwnerByEmail(String email);
}
