package com.autobots.app.exceptions;

public class MissingDocumentException extends RuntimeException{
    public MissingDocumentException(String message) {
        super(message);
    }
}
