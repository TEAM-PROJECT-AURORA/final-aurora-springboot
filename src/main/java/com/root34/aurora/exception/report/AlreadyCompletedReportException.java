package com.root34.aurora.exception.report;

public class AlreadyCompletedReportException extends RuntimeException{

    public AlreadyCompletedReportException() {
        super();
    }

    public AlreadyCompletedReportException(String message) {
        super(message);
    }

    public AlreadyCompletedReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyCompletedReportException(Throwable cause) {
        super(cause);
    }
}
