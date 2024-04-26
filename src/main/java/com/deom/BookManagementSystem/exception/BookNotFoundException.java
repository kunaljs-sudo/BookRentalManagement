package com.deom.BookManagementSystem.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super();
    }

    public BookNotFoundException(String msg) {
        super(msg);
    }
}
