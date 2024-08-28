package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCreateBook() throws Exception {
        String bookJson = "{\"title\": \"New Book\", \"author\": \"Author\", \"price\": 20.0, \"isbn\": \"654321\"}";

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isCreated());
    }
}
