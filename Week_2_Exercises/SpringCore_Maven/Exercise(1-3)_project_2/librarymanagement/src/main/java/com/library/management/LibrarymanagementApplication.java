package com.library.management;

import com.library.management.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibrarymanagementApplication {

    public static void main(String[] args) {
        System.out.println("Starting Library Management Application...");

        // Load the Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get the BookService bean
        BookService bookService = (BookService) context.getBean("bookService");

        // Test the configuration
        bookService.performService();

        System.out.println("Library Management Application Started Successfully.");
    }
}
