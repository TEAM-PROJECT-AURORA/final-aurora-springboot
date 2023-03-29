package com.root34.aurora.exception.report;

/**
	@ClassName : AlreadyCompletedReportException
	@Date : 2023-03-28
	@Writer : 김수용 
	@Description : 이미 완료된 보고에 대한 예외
*/
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
