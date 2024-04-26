package com.deom.BookManagementSystem.exception;

public class UnableToReturnBookException extends RuntimeException {
    public UnableToReturnBookException() {
        super();
    }

    public UnableToReturnBookException(String msg) {
        super(msg);
    }

}
