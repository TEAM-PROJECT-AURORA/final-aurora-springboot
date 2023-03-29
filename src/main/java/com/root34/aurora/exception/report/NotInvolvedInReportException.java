package com.root34.aurora.exception.report;

/**
	@ClassName : NotInvolvedInReportException
	@Date : 2023-03-28
	@Writer : 김수용
	@Description : 보고 관련자가 아닐때 예외
*/
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
