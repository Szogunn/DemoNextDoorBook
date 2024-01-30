package com.szogunn.demonextdoorbook.servicesImpl;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import com.szogunn.demonextdoorbook.dtos.ExchangeDTO;
import com.szogunn.demonextdoorbook.jwt.UserDetailsImpl;
import com.szogunn.demonextdoorbook.model.Book;
import com.szogunn.demonextdoorbook.model.Exchange;
import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.payloads.MessageResponse;
import com.szogunn.demonextdoorbook.repositories.BookRepository;
import com.szogunn.demonextdoorbook.repositories.ExchangeRepository;
import com.szogunn.demonextdoorbook.repositories.UserRepository;
import com.szogunn.demonextdoorbook.services.ExchangeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final BookRepository bookRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;

    public ExchangeServiceImpl(BookRepository bookRepository, ExchangeRepository exchangeRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.exchangeRepository = exchangeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> exchange(Long bookId, LocalDate endRent) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByLogin(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + userDetails.getUsername()));
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        List<Exchange> bookExchanges = exchangeRepository.findExchangeByBook_IdAndEndRentAfter(bookId, LocalDate.now());
        if (optionalBook.isPresent() && bookExchanges.isEmpty()) {
            Book book = optionalBook.get();
            Exchange exchange = new Exchange(user, book, LocalDate.now(), endRent);
            exchangeRepository.save(exchange);
            ExchangeDTO exchangeDTO = new ExchangeDTO(new BookDTO.Builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .ISBN(book.getISBN())
                    .numPages(book.getNumPages())
                    .language(book.getLanguage())
                    .publisher(book.getPublisher())
                    .publishedYear(book.getPublishedYear())
                    .authors(book.getAuthors())
                    .owner(book.getOwner().getLogin()).build()
                    , LocalDate.now()
                    , endRent);
            return new ResponseEntity<>(exchangeDTO, HttpStatus.OK);
        } else if (!bookExchanges.isEmpty()){
            return new ResponseEntity<>(new MessageResponse("Book has been already borrower by another User"), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(new MessageResponse("smth went wrong"), HttpStatus.BAD_REQUEST);
    }
}
