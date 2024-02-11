package com.szogunn.demonextdoorbook.mappers;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import com.szogunn.demonextdoorbook.dtos.ExchangeDTO;
import com.szogunn.demonextdoorbook.dtos.UserDTO;
import com.szogunn.demonextdoorbook.model.Book;
import com.szogunn.demonextdoorbook.model.Exchange;
import com.szogunn.demonextdoorbook.model.User;
import org.springframework.stereotype.Component;

import static com.szogunn.demonextdoorbook.mappers.MappersUtil.getUserRate;

@Component
public class ExchangeToExchangeDTOMapperImpl implements Mapper<Exchange, ExchangeDTO> {

    @Override
    public ExchangeDTO map(Exchange source) {
        Book book = source.getBook();
        User owner = book.getOwner();
        User renter = source.getRenter();

        UserDTO ownerDTO = new UserDTO(owner.getId(), owner.getLogin(), getUserRate(owner));
        BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getISBN(), book.getNumPages(), book.getLanguage(), book.getPublisher(), book.getPublishedYear(), ownerDTO);
        UserDTO renterDTO = new UserDTO(renter.getId(), renter.getLogin(), getUserRate(renter));

        return new ExchangeDTO(source.getId(), bookDTO, source.getStartRent(), source.getEndRent(), source.getStatus(),renterDTO);
    }


}
