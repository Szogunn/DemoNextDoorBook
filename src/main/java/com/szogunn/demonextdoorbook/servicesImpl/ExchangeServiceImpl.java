package com.szogunn.demonextdoorbook.servicesImpl;

import com.szogunn.demonextdoorbook.dtos.ExchangeDTO;
import com.szogunn.demonextdoorbook.mappers.Mapper;
import com.szogunn.demonextdoorbook.mappers.MapperFactory;
import com.szogunn.demonextdoorbook.model.Book;
import com.szogunn.demonextdoorbook.model.Exchange;
import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.payloads.MessageResponse;
import com.szogunn.demonextdoorbook.payloads.Response;
import com.szogunn.demonextdoorbook.repositories.BookRepository;
import com.szogunn.demonextdoorbook.repositories.ExchangeRepository;
import com.szogunn.demonextdoorbook.services.ExchangeService;
import com.szogunn.demonextdoorbook.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final BookRepository bookRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserService userService;
    private final MapperFactory mapperFactory;

    public ExchangeServiceImpl(BookRepository bookRepository, ExchangeRepository exchangeRepository, UserService userService, MapperFactory mapperFactory) {
        this.bookRepository = bookRepository;
        this.exchangeRepository = exchangeRepository;
        this.userService = userService;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public ResponseEntity<?> exchange(Long bookId, LocalDate endRent) {
        User user = userService.getAuthenticatedUser();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        List<Exchange> bookExchanges = exchangeRepository.findExchangeByBook_IdAndEndRentAfter(bookId, LocalDate.now());

        if (optionalBook.isPresent() && bookExchanges.isEmpty()) {
            Exchange exchange = new Exchange(user, optionalBook.get(), LocalDate.now(), endRent);
            exchangeRepository.save(exchange);

            Mapper<Exchange, ExchangeDTO> mapper = mapperFactory.getMapper(Exchange.class, ExchangeDTO.class);
            if (mapper != null) {
                ExchangeDTO exchangeDTO = mapper.map(exchange);
                return new ResponseEntity<>(new Response<>(exchangeDTO, "The Exchange was successful"), HttpStatus.CREATED);
            }

        } else if (!bookExchanges.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("Book has been already borrower by another User"), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(new MessageResponse("smth went wrong"), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> getUserBooksRentalHistory() {
        User user = userService.getAuthenticatedUser();
        List<Exchange> userBooksExchanged = exchangeRepository.findExchangesByRenter_IdOrBook_User_Id(user.getId(), user.getId());
        Mapper<Exchange, ExchangeDTO> mapper = mapperFactory.getMapper(Exchange.class, ExchangeDTO.class);
        List<ExchangeDTO> exchangeDTOList = userBooksExchanged.stream()
                .map(mapper::map)
                .toList();
        return new ResponseEntity<>(exchangeDTOList, HttpStatus.OK);
    }
}
