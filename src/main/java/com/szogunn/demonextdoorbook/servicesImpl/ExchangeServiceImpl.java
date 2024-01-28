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
    public ResponseEntity<?> exchange(BookDTO bookDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByLogin(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + userDetails.getUsername()));
        Optional<Book> book = bookRepository.findById(bookDTO.id());
        if (book.isPresent()) {
            Exchange exchange = new Exchange(user, book.get(), LocalDate.now(), LocalDate.now().plusDays(5));
            exchangeRepository.save(exchange);
            ExchangeDTO exchangeDTO = new ExchangeDTO(bookDTO, LocalDate.now(), LocalDate.now().plusDays(5));
            return new ResponseEntity<>(exchangeDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("smth went wrong"), HttpStatus.BAD_REQUEST);
    }
}
