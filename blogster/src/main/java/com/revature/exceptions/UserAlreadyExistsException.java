package com.revature.exceptions;

public class UserAlreadyExistsException extends  RuntimeException {
    public UserAlreadyExistsException(String e) {
        super(e);
    }
}
