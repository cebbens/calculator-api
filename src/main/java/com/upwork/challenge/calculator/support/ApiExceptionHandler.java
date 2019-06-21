package com.upwork.challenge.calculator.support;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;

/**
 * Spring controller advice which maps {@code Throwables} to {@code ResponseEntities} to be returned as responses.
 */
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ApiResponse> handle(MethodArgumentTypeMismatchException exception) {
        return buildBadRequestResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ApiResponse> handle(IllegalArgumentException exception) {
        return buildBadRequestResponseEntity(exception.getMessage());
    }

    @ExceptionHandler({ArithmeticException.class})
    ResponseEntity<ApiResponse> handle(ArithmeticException exception) {
        return buildBadRequestResponseEntity(exception.getMessage());
    }

    private ResponseEntity<ApiResponse> buildBadRequestResponseEntity(String exceptionMessage) {
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .errors(Collections.singletonList(exceptionMessage))
                        .build());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse> handle(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.builder()
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .errors(Collections.singletonList(exception.getMessage()))
                        .build());
    }
}
