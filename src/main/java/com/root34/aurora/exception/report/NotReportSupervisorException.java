package com.root34.aurora.exception.report;

/**
	@ClassName : NotReportSupervisorException
	@Date : 2023-03-28
	@Writer : 김수용
	@Description : 해당 보고 책임자가 아닐때 예외
*/
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
