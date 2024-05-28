package org.rmerezha.di.exception;

public class NoDefaultConstructorException extends RuntimeException {

    public NoDefaultConstructorException(String message) {
        super(message);
    }
}
