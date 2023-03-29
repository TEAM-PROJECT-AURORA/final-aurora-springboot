package com.root34.aurora.exception.report;

/**
	@ClassName : InvalidReportTypeException
	@Date : 2023-03-28
	@Writer : 김수용
	@Description : 적합하지않은 보고 타입에 대한 예외
*/
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
