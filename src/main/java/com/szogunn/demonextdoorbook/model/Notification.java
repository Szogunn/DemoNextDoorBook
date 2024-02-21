package com.szogunn.demonextdoorbook.model;

import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    private User receiver;

    public Notification(String description, User receiver) {
        this.description = description;
        this.receiver = receiver;
    }

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User destination) {
        this.receiver = destination;
    }
}
