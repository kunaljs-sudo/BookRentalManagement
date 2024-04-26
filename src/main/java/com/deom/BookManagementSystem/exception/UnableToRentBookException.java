package com.deom.BookManagementSystem.exception;

public class UnableToRentBookException extends RuntimeException {
    public UnableToRentBookException() {
        super();
    }

    public UnableToRentBookException(String msg) {
        super(msg);
    }

}
