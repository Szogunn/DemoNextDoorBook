package com.szogunn.demonextdoorbook.servicesImpl;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import com.szogunn.demonextdoorbook.model.Book;
import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.payloads.MessageResponse;
import com.szogunn.demonextdoorbook.repositories.BookRepository;
import com.szogunn.demonextdoorbook.repositories.UserRepository;
import com.szogunn.demonextdoorbook.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> addBook(BookDTO bookDTO, String username) {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + username));
        Book book = new Book();
        book.setISBN(bookDTO.ISBN());
        book.setTittle(bookDTO.tittle());
        book.setLanguage(bookDTO.language());
        book.setNumPages(bookDTO.numPages());
        book.setPublishedYear(bookDTO.publishedYear());
        book.setPublisher(bookDTO.publisher());
        book.setAuthors(bookDTO.authors());
        book.setOwner(user);

        bookRepository.save(book);

        return new ResponseEntity<>(new MessageResponse("Book has been Added"), HttpStatus.CREATED);
    }
}
