package com.root34.aurora.exception;

/**
	@ClassName : UpdateFailedException
	@Date : 2023-03-28
	@Writer : 김수용
	@Description : 수정 실패시 예외
*/
public class UpdateFailedException extends RuntimeException{

    public UpdateFailedException() {
        super();
    }

    public UpdateFailedException(String message) {
        super(message);
    }

    public UpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateFailedException(Throwable cause) {
        super(cause);
    }
}
