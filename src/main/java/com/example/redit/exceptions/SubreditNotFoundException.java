package com.example.redit.exceptions;

public class SubreditNotFoundException extends RuntimeException {
    public SubreditNotFoundException(String message) {
        super(message);
    }
}