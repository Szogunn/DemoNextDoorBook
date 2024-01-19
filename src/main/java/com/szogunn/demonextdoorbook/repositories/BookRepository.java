package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
