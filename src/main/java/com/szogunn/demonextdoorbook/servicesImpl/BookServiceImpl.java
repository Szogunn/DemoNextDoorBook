package com.szogunn.demonextdoorbook.servicesImpl;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import com.szogunn.demonextdoorbook.jwt.UserDetailsImpl;
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

import java.util.List;

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
        book.setTitle(bookDTO.title());
        book.setLanguage(bookDTO.language());
        book.setNumPages(bookDTO.numPages());
        book.setPublishedYear(bookDTO.publishedYear());
        book.setPublisher(bookDTO.publisher());
        book.setAuthors(bookDTO.authors());
        book.setOwner(user);

        bookRepository.save(book);

        return new ResponseEntity<>(new MessageResponse("Book has been Added"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<BookDTO>> showAllBooks(UserDetailsImpl userDetails) {
        List<Book> books = bookRepository.findBooksByUserId(userDetails.getId());
        List<BookDTO> userBooks = books.stream()
                .map(book -> new BookDTO.Builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .ISBN(book.getISBN())
                        .numPages(book.getNumPages())
                        .language(book.getLanguage())
                        .publisher(book.getPublisher())
                        .publishedYear(book.getPublishedYear())
                        .authors(book.getAuthors())
                        .build())
                .toList();
        return new ResponseEntity<>(userBooks, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BookDTO>> showNeighboursBooks(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<BookDTO> neighboursBooks = bookRepository.findAll().stream()//nie może być tak że pobieram całą baze danych. Są to zbędne dane z perspektywy programu. Być może skorzystać z wczytywania z bazy danych skolejkowanego
                .filter(book -> !book.getOwner().getId().equals(userId)) //TODO do poprawienia, na podstawie np filtra odległościowego będą wczytywane te książki których właścieciele są odpowiednio blisko. Wcześniej trzeba zaimplementować uzupełnienie profilu o adres
                .map(book -> new BookDTO.Builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .ISBN(book.getISBN())
                        .numPages(book.getNumPages())
                        .language(book.getLanguage())
                        .publisher(book.getPublisher())
                        .publishedYear(book.getPublishedYear())
                        .authors(book.getAuthors())
                        .owner(book.getOwner().getLogin())
                        .build())
                .toList();
        return new ResponseEntity<>(neighboursBooks, HttpStatus.OK);
    }
}
