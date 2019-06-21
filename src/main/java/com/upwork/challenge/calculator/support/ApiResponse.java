package com.upwork.challenge.calculator.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Generic response that represents an API endpoint response's body.
 *
 * @param <T> Generic type.
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private List<String> errors;

    private T data;
}
