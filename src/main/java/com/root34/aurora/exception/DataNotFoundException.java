package com.root34.aurora.exception;

/**
	@ClassName : DataNotFoundException
	@Date : 2023-03-28
	@Writer : 김수용
	@Description : 데이터 조회 실패시 예외
*/
public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }
}
