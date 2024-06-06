package com.revature.exceptions;

public class InvalidBlogException extends RuntimeException{
    public InvalidBlogException(String message) {
        super(message);
    }
}
