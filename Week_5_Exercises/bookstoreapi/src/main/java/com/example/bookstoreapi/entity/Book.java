package com.example.bookstoreapi.entity;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private Double price;
    private String isbn;

    // No-args constructor is needed by JPA
    public Book() {}

    // Constructor for convenience in tests or other uses
    public Book(Long id, String title, String author, Double price, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
    }
}
