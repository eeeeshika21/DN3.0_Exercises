package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetBookById() {
        Book book = new Book(1L, "Test Book", "Author", 10.99, "suuudu");
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookController.getBookById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }
}
