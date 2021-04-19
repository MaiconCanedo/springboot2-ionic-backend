package com.nelioalves.cursomc.domain.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, Object... parameters) {
        super(String.format(message, parameters));
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}