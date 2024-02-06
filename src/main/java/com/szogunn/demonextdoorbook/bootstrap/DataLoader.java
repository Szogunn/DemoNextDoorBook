package com.szogunn.demonextdoorbook.bootstrap;

import com.szogunn.demonextdoorbook.model.Book;
import com.szogunn.demonextdoorbook.model.Exchange;
import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.repositories.BookRepository;
import com.szogunn.demonextdoorbook.repositories.ExchangeRepository;
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
    private final ExchangeRepository exchangeRepository;
    private final PasswordEncoder encoder;

    public DataLoader(UserRepository userRepository, BookRepository bookRepository, ExchangeRepository exchangeRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.exchangeRepository = exchangeRepository;
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


        userRepository.save(user1);


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


        userRepository.save(user2);
        bookRepository.saveAll(List.of(book1, book2, book3, book4));

        createMoreDate();

        createExchanges(user1, book4);
    }

    private void createExchanges(User user, Book book){
        Exchange exchange = new Exchange(user, book, LocalDate.now(), LocalDate.now().plusDays(4));
        exchangeRepository.save(exchange);
    }

    private void createMoreDate(){
        // Tworzenie użytkowników
        User user3 = new User("Kamil", encoder.encode("kamil123"),"kamil@example.com");
        User user4 = new User("Ewa", encoder.encode("ewa456"),"ewa@example.com");

// Tworzenie książek dla użytkownika 3
        Book book5 = new Book();
        book5.setISBN("76543212");
        book5.setTitle("Algorytmy i struktury danych");
        book5.setLanguage("Polski");
        book5.setNumPages(400);
        book5.setPublishedYear(LocalDate.of(2005, 12, 10));
        book5.setPublisher("Wydawnictwo Naukowe PWN");
        book5.setOwner(user3);

        Book book6 = new Book();
        book6.setISBN("87654321");
        book6.setTitle("Introduction to Algorithms");
        book6.setLanguage("English");
        book6.setNumPages(1000);
        book6.setPublishedYear(LocalDate.of(2009, 8, 30));
        book6.setPublisher("The MIT Press");
        book6.setOwner(user3);

// Tworzenie książek dla użytkownika 4
        Book book7 = new Book();
        book7.setISBN("98765432");
        book7.setTitle("Programowanie w C++");
        book7.setLanguage("Polski");
        book7.setNumPages(350);
        book7.setPublishedYear(LocalDate.of(2010, 5, 20));
        book7.setPublisher("Helion");
        book7.setOwner(user4);

        Book book8 = new Book();
        book8.setISBN("10987654");
        book8.setTitle("C++ Primer");
        book8.setLanguage("English");
        book8.setNumPages(900);
        book8.setPublishedYear(LocalDate.of(2015, 11, 25));
        book8.setPublisher("Addison-Wesley Professional");
        book8.setOwner(user4);

// Zapisywanie użytkowników i książek do repozytorium
        userRepository.saveAll(List.of(user3, user4));
        bookRepository.saveAll(List.of(book5, book6, book7, book8));

    }

}
