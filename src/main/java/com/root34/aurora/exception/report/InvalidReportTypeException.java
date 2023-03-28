package com.root34.aurora.exception.report;

public class InvalidReportTypeException extends RuntimeException{

    public InvalidReportTypeException() {
        super();
    }

    public InvalidReportTypeException(String message) {
        super(message);
    }

    public InvalidReportTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReportTypeException(Throwable cause) {
        super(cause);
    }
}
