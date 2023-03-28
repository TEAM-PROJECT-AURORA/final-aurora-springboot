package com.root34.aurora.exception;

public class CreationFailedException extends RuntimeException{

    public CreationFailedException() {
        super();
    }

    public CreationFailedException(String message) {
        super(message);
    }

    public CreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreationFailedException(Throwable cause) {
        super(cause);
    }
}
