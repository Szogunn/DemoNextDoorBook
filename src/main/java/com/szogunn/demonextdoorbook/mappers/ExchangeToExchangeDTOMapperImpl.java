package com.szogunn.demonextdoorbook.mappers;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import com.szogunn.demonextdoorbook.dtos.ExchangeDTO;
import com.szogunn.demonextdoorbook.dtos.UserDTO;
import com.szogunn.demonextdoorbook.model.Book;
import com.szogunn.demonextdoorbook.model.Exchange;
import com.szogunn.demonextdoorbook.model.User;
import org.springframework.stereotype.Component;

@Component
public class ExchangeToExchangeDTOMapperImpl implements Mapper<Exchange, ExchangeDTO> {

    @Override
    public ExchangeDTO map(Exchange source) {
        Book book = source.getBook();
        User user = book.getOwner();
        UserDTO userDTO = new UserDTO(user.getId(), user.getLogin());
        BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getISBN(), book.getNumPages(), book.getLanguage(), book.getPublisher(), book.getPublishedYear(), userDTO);

        return new ExchangeDTO(bookDTO, source.getStartRent(), source.getEndRent());
    }
}
