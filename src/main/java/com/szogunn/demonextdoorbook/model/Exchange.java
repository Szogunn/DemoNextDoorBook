package com.szogunn.demonextdoorbook.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User renter;
    @ManyToOne
    private Book book;
    private LocalDate startRent;
    private LocalDate endRent;
    @Enumerated(value = EnumType.STRING)
    private ExchangeStatus status;
    @Enumerated(value = EnumType.STRING)
    private Rate rate;

    public Exchange(User renter, Book book, LocalDate startRent, LocalDate endRent) {
        this.renter = renter;
        this.book = book;
        this.startRent = startRent;
        this.endRent = endRent;
        this.status = ExchangeStatus.PENDING;
    }

    public Exchange() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getRenter() {
        return renter;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getStartRent() {
        return startRent;
    }

    public void setStartRent(LocalDate startRent) {
        this.startRent = startRent;
    }

    public LocalDate getEndRent() {
        return endRent;
    }

    public void setEndRent(LocalDate endRent) {
        this.endRent = endRent;
    }

    public ExchangeStatus getStatus() {
        return status;
    }

    public void setStatus(ExchangeStatus status) {
        this.status = status;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}
