package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        logger.info("Fetching all books...");
        List<Book> books = bookRepository.findAll();
        logger.info("Books fetched: {}", books);
        return books;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        logger.info("Adding a new book: {}", book);
        Book savedBook = bookRepository.save(book);
        logger.info("Book added: {}", savedBook);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        logger.info("Fetching book with ID: {}", id);
        Optional<Book> book = bookRepository.findById(id);
        return book.map(b -> {
            logger.info("Book fetched: {}", b);
            return new ResponseEntity<>(b, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.warn("Book with ID {} not found.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        logger.info("Updating book with ID: {} to {}", id, book);
        if (!bookRepository.existsById(id)) {
            logger.warn("Book with ID {} not found for update.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        logger.info("Book updated: {}", updatedBook);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        logger.info("Deleting book with ID: {}", id);
        if (!bookRepository.existsById(id)) {
            logger.warn("Book with ID {} not found for deletion.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
        logger.info("Book with ID {} deleted.", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
