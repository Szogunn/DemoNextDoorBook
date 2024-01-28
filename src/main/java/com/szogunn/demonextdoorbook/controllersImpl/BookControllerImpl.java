package com.szogunn.demonextdoorbook.controllersImpl;

import com.szogunn.demonextdoorbook.controllers.BookController;
import com.szogunn.demonextdoorbook.dtos.BookDTO;
import com.szogunn.demonextdoorbook.jwt.UserDetailsImpl;
import com.szogunn.demonextdoorbook.payloads.MessageResponse;
import com.szogunn.demonextdoorbook.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookControllerImpl implements BookController {
    private final BookService bookService;

    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<?> addBook(BookDTO bookDTO, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName();
            return bookService.addBook(bookDTO,username);
        }
        return new ResponseEntity<>(new MessageResponse("User is not authenticated, please Log in"), HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<List<BookDTO>> showAllBooks() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookService.showAllBooks(userDetails);
    }
}
