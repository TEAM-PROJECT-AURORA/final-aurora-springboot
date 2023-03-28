package com.root34.aurora.exception.report;

public class NotInvolvedInReportException extends RuntimeException{

    public NotInvolvedInReportException() {
        super();
    }

    public NotInvolvedInReportException(String message) {
        super(message);
    }

    public NotInvolvedInReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotInvolvedInReportException(Throwable cause) {
        super(cause);
    }
}
