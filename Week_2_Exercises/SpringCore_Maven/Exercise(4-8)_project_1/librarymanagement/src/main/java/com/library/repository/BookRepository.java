package com.library.repository;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    public void print() {
        System.out.println("BookRepository is working.");
    }
}
