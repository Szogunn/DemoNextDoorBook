package com.szogunn.demonextdoorbook.mappers;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import com.szogunn.demonextdoorbook.dtos.UserDTO;
import com.szogunn.demonextdoorbook.model.Book;
import com.szogunn.demonextdoorbook.model.User;
import org.springframework.stereotype.Component;

@Component
public class BookToBookDTOMapperImpl implements Mapper<Book, BookDTO> {
    @Override
    public BookDTO map(Book source) {
        User user = source.getOwner();
        UserDTO userDTO = new UserDTO(user.getId(), user.getLogin());
        return new BookDTO(source.getId()
                , source.getTitle(), source.getISBN()
                , source.getNumPages(), source.getLanguage()
                , source.getPublisher()
                , source.getPublishedYear()
                , userDTO
        );
    }
}
