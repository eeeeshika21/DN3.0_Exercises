package com.example.bookstoreapi;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Convert Entity to DTO
    private BookDTO convertToDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    // Convert DTO to Entity
    private Book convertToEntity(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    // POST: Create a new book using JSON request body
    @PostMapping("/create")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        book = bookRepository.save(book); // Save the book entity
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(book));
    }

    // GET: Get all books
    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // GET: Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
            .map(book -> ResponseEntity.ok(convertToDTO(book)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: Update a book
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return bookRepository.findById(id)
            .map(existingBook -> {
                Book book = convertToEntity(bookDTO);
                book.setId(existingBook.getId()); // Preserve the existing ID
                book = bookRepository.save(book);
                return ResponseEntity.ok(convertToDTO(book));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
    
