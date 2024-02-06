package com.szogunn.demonextdoorbook.servicesImpl;

import com.szogunn.demonextdoorbook.dtos.ExchangeDTO;
import com.szogunn.demonextdoorbook.mappers.Mapper;
import com.szogunn.demonextdoorbook.mappers.MapperFactory;
import com.szogunn.demonextdoorbook.model.Book;
import com.szogunn.demonextdoorbook.model.Exchange;
import com.szogunn.demonextdoorbook.model.ExchangeStatus;
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
    private final Mapper<Exchange, ExchangeDTO> exchangeMapper;

    public ExchangeServiceImpl(BookRepository bookRepository, ExchangeRepository exchangeRepository, UserService userService, MapperFactory mapperFactory) {
        this.bookRepository = bookRepository;
        this.exchangeRepository = exchangeRepository;
        this.userService = userService;
        this.exchangeMapper = mapperFactory.getMapper(Exchange.class, ExchangeDTO.class);
    }

    @Override
    public ResponseEntity<?> exchange(Long bookId, LocalDate endRent) {
        User user = userService.getAuthenticatedUser();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        List<Exchange> bookExchanges = exchangeRepository.findExchangeByBook_IdAndEndRentAfter(bookId, LocalDate.now());

        if (optionalBook.isPresent() && bookExchanges.isEmpty() && !isUserOwnTheBook(user, optionalBook.get())) {
            Exchange exchange = new Exchange(user, optionalBook.get(), LocalDate.now(), endRent);
            exchangeRepository.save(exchange);

            ExchangeDTO exchangeDTO = exchangeMapper.map(exchange);
            return new ResponseEntity<>(new Response<>(exchangeDTO, "The Exchange was successful"), HttpStatus.CREATED);

        } else if (!bookExchanges.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("Book has been already borrower by another User"), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(new MessageResponse("smth went wrong"), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> changeExchangeStatus(Long exchangeId, ExchangeStatus newStatus) {
        User user = userService.getAuthenticatedUser();
        Optional<Exchange> optionalExchange = exchangeRepository.findById(exchangeId);
        if (optionalExchange.isPresent() && isUserOwnTheBook(user, optionalExchange.get().getBook())){
            Exchange exchange = optionalExchange.get();

            ExchangeStatus exchangeStatus = exchange.getStatus();
            if (exchangeStatus.equals(ExchangeStatus.PENDING) && (newStatus.equals(ExchangeStatus.ACCEPTED) || newStatus.equals(ExchangeStatus.REJECTED))){
                exchange.setStatus(newStatus);
                exchangeRepository.save(exchange);

                ExchangeDTO exchangeDTO = exchangeMapper.map(exchange);
                return new ResponseEntity<>(new Response(exchangeDTO, "Status has changed"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new MessageResponse("smth went wrong"), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<ExchangeDTO>> getUserBooksRentalHistory(ExchangeStatus [] statuses) {
        User user = userService.getAuthenticatedUser();
        List<Exchange> userBooksExchanged = exchangeRepository.findExchangesByBook_User_IdAndStatusIsIn(user.getId(), statuses);
        List<ExchangeDTO> exchangeDTOList = userBooksExchanged.stream()
                .map(exchangeMapper::map)
                .toList();
        return new ResponseEntity<>(exchangeDTOList, HttpStatus.OK);
    }

    private boolean isUserOwnTheBook(User user, Book book){
        return book.getOwner().equals(user);
    }
}
