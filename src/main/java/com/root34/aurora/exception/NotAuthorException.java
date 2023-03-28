package com.root34.aurora.exception;

public class NotAuthorException extends RuntimeException{

    public NotAuthorException() {
        super();
    }

    public NotAuthorException(String message) {
        super(message);
    }

    public NotAuthorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorException(Throwable cause) {
        super(cause);
    }
}
