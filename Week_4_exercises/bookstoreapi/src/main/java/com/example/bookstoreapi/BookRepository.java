package com.example.bookstoreapi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom queries can be added here if needed
}
