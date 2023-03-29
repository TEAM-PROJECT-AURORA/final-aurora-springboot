package com.root34.aurora.exception;

/**
	@ClassName : NotAuthorException
	@Date : 2023-03-28
	@Writer : 김수용
	@Description : 해당 객체의 작성자가 아닐때 예외
*/
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
