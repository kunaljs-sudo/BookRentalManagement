package com.deom.BookManagementSystem.exception;

public class UserNameNoFoundException extends RuntimeException {
    public UserNameNoFoundException() {
        super();
    }

    public UserNameNoFoundException(String msg) {
        super(msg);
    }
}
