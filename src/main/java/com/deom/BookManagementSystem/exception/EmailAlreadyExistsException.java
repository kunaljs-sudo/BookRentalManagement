package com.deom.BookManagementSystem.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super();
    }

    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }

}
