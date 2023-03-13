package com.root34.aurora.exception;

import com.root34.aurora.exception.dto.ApiExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    // 오버로딩 사용했넹
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ApiExceptionDto> exceptionHandler(LoginFailedException e) {
        //e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<ApiExceptionDto> exceptionHandler(DuplicatedUsernameException e) {
        //e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ApiExceptionDto> exceptionHandler(TokenException e) {
        //e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiExceptionDto(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ApiExceptionDto> exceptionHandler(WrongPasswordException e) {
        //e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}