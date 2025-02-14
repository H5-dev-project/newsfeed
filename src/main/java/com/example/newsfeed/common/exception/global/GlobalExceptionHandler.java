package com.example.newsfeed.common.exception.global;

import com.example.newsfeed.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleException(Exception ex) {
        log.error("예외 발생: {}", ex.getMessage(), ex);

        HttpStatus httpStatus;
        String errorCode;
        String errorMessage;

        if (ex instanceof IllegalArgumentException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorCode = "BAD_REQUEST";
            errorMessage = ex.getMessage();
        } else if (ex instanceof NullPointerException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorCode = "NULL_POINTER";
            errorMessage = "필수 값이 누락되었습니다.";
        } else if (ex instanceof IllegalStateException) {
            httpStatus = HttpStatus.CONFLICT;
            errorCode = "INVALID_STATE";
            errorMessage = ex.getMessage();
        } else if (ex instanceof MethodArgumentNotValidException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorCode = "BAD_REQUEST";
            errorMessage = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().stream()
                    .findFirst()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .orElse("입력값이 유효하지 않습니다.");
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            errorCode = "INTERNAL_SERVER_ERROR";
            errorMessage = "서버 내부 오류가 발생했습니다.";
        }

        return ResponseEntity.status(httpStatus)
                .body(ResponseDto.fail(httpStatus, errorCode, errorMessage));
    }
}
