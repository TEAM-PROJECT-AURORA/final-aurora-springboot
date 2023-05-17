package com.root34.aurora.exception;

/**
	@ClassName : CreationFailedException
	@Date : 2023-03-28
	@Writer : 김수용
	@Description : 작성, 삽입, 추가 실패시 예외
*/
public class CreationFailedException extends RuntimeException{

    public CreationFailedException() {
        super();
    }

    public CreationFailedException(String message) {
        super(message);
    }

    public CreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreationFailedException(Throwable cause) {
        super(cause);
    }
}
