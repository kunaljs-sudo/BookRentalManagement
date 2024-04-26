package com.deom.BookManagementSystem.exception;

public class BadAuthenticationException extends RuntimeException {
    public BadAuthenticationException() {
        super();
    }

    public BadAuthenticationException(String msg) {
        super(msg);
    }
}
