package com.szogunn.demonextdoorbook.dtos;

import com.szogunn.demonextdoorbook.model.Author;

import java.time.LocalDate;
import java.util.Set;

public record BookDTO(
        Long id,
        String title,
        String ISBN,
        int numPages,
        String language,
        String publisher,
        LocalDate publishedYear,
        Set<Author> authors,
        String owner
) {
    public static class Builder {
        private Long id;
        private String title;
        private String ISBN;
        private int numPages;
        private String language;
        private String publisher;
        private LocalDate publishedYear;
        private Set<Author> authors;
        private String owner;

        public Builder(){
        }

        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder title(String title){
            this.title = title;
            return this;
        }
        public Builder ISBN(String ISBN){
            this.ISBN = ISBN;
            return this;
        }
        public Builder numPages(int numPages){
            this.numPages = numPages;
            return this;
        }
        public Builder language(String language){
            this.language = language;
            return this;
        }
        public Builder publisher(String publisher){
            this.publisher = publisher;
            return this;
        }
        public Builder publishedYear(LocalDate publishedYear){
            this.publishedYear = publishedYear;
            return this;
        }
        public Builder authors(Set<Author> authors){
            this.authors = authors;
            return this;
        }
        public Builder owner(String owner){
            this.owner = owner;
            return this;
        }

        public BookDTO build() {
            return new BookDTO(id, title, ISBN, numPages, language, publisher, publishedYear, authors, owner);
        }

    }
}
