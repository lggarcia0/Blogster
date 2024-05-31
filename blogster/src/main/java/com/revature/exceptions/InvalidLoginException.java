package com.revature.exceptions;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String e) {
        super(e);
    }
}
