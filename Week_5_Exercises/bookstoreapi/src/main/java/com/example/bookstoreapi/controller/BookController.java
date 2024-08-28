package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Books")
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Get all books", notes = "Retrieve a list of all books")
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @ApiOperation(value = "Get book by ID", notes = "Retrieve a single book by its ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved book"),
        @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
        @ApiParam(value = "ID of the book to be retrieved", required = true) @PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Create a new book", notes = "Add a new book to the store")
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @ApiOperation(value = "Update an existing book", notes = "Update details of an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
        @ApiParam(value = "ID of the book to be updated", required = true) @PathVariable Long id,
        @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @ApiOperation(value = "Delete a book", notes = "Remove a book from the store by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
        @ApiParam(value = "ID of the book to be deleted", required = true) @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
