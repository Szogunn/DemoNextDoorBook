package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findBooksByUserId(Long id);
}
