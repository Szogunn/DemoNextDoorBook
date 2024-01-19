package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
