package com.szogunn.demonextdoorbook.bootstrap;

import com.szogunn.demonextdoorbook.model.Book;
import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.repositories.BookRepository;
import com.szogunn.demonextdoorbook.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder encoder;

    public DataLoader(UserRepository userRepository, BookRepository bookRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Mateusz", encoder.encode("password"),"mati@mati.pl");
        Book book1 = new Book();
        book1.setISBN("3213123");
        book1.setTitle("Podróże");
        book1.setLanguage("Polski");
        book1.setNumPages(200);
        book1.setPublishedYear(LocalDate.of(1990,6,12));
        book1.setPublisher("Helion");
        book1.setOwner(user1);

        Book book2 = new Book();
        book2.setISBN("43214123");
        book2.setTitle("Wycieczki");
        book2.setLanguage("Angielski");
        book2.setNumPages(190);
        book2.setPublishedYear(LocalDate.of(2000,6,12));
        book2.setPublisher("Helion");
        book2.setOwner(user1);
        Set<Book> booksUser1 = Set.of(book1, book2);

        userRepository.save(user1);
        bookRepository.saveAll(booksUser1);

        User user2 = new User("Anna", encoder.encode("123456"),"anna@example.com");
        Book book3 = new Book();
        book3.setISBN("54321234");
        book3.setTitle("Historia Polski");
        book3.setLanguage("Polski");
        book3.setNumPages(300);
        book3.setPublishedYear(LocalDate.of(1985, 3, 20));
        book3.setPublisher("PWN");
        book3.setOwner(user2);

        Book book4 = new Book();
        book4.setISBN("65432123");
        book4.setTitle("English Grammar in Use");
        book4.setLanguage("English");
        book4.setNumPages(350);
        book4.setPublishedYear(LocalDate.of(1995, 9, 15));
        book4.setPublisher("Cambridge University Press");
        book4.setOwner(user2);
        Set<Book> booksUser2 = Set.of(book3, book4);


        userRepository.save(user2);
        bookRepository.saveAll(List.of(book1, book2, book3, book4));

    }
}
