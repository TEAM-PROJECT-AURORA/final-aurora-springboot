package com.root34.aurora.exception.report;

public class NotReportSupervisorException extends RuntimeException{

    public NotReportSupervisorException() {
        super();
    }

    public NotReportSupervisorException(String message) {
        super(message);
    }

    public NotReportSupervisorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotReportSupervisorException(Throwable cause) {
        super(cause);
    }
}
