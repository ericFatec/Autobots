package com.autobots.app.exceptions;

public class InactiveCredentialException extends RuntimeException {
    public InactiveCredentialException() {
        super("Credencial está inativa.");
    }

    public InactiveCredentialException(String message) {
        super(message);
    }
}
