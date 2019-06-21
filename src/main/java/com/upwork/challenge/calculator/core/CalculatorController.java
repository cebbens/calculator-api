package com.upwork.challenge.calculator.core;

import com.upwork.challenge.calculator.support.ApiResponse;
import com.upwork.challenge.calculator.support.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Controller which maps each API endpoint's path to its corresponding handler.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "${api.base-path}/calculator", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculatorController {

    private static final String OK = "OK";

    private final CalculatorService calculatorService;

    /**
     * Addition operation handler. It supports up to three operands as path parameters - the third operand is optional.
     *
     * @param operand1 First operand.
     * @param operand2 Second operand.
     * @param operand3 Third operand (optional).
     * @return {@code ResponseEntity<ApiResponse<Result>>} representing the response.
     */
    @GetMapping(path = {"add/{operand1}/{operand2}", "add/{operand1}/{operand2}/{operand3}"})
    public ResponseEntity<ApiResponse<Result>> add(@PathVariable BigDecimal operand1,
                                                   @PathVariable BigDecimal operand2,
                                                   @PathVariable(required = false) BigDecimal operand3) {
        return ResponseEntity.ok(
                ApiResponse.<Result>builder()
                        .message(OK)
                        .data(calculatorService.add(operand1, operand2, operand3))
                        .build());
    }

    /**
     * Subtraction operation handler. It supports up to three operands as path parameters - the third operand is optional.
     *
     * @param operand1 First operand.
     * @param operand2 Second operand.
     * @param operand3 Third operand (optional).
     * @return {@code ResponseEntity<ApiResponse<Result>>} representing the response.
     */
    @GetMapping(path = {"subtract/{operand1}/{operand2}", "subtract/{operand1}/{operand2}/{operand3}"})
    public ResponseEntity<ApiResponse<Result>> subtract(@PathVariable BigDecimal operand1,
                                                        @PathVariable BigDecimal operand2,
                                                        @PathVariable(required = false) BigDecimal operand3) {
        return ResponseEntity.ok(
                ApiResponse.<Result>builder()
                        .message(OK)
                        .data(calculatorService.subtract(operand1, operand2, operand3))
                        .build());
    }

    /**
     * Multiplication operation handler. It supports up to three operands as path parameters - the third operand is optional.
     *
     * @param operand1 First operand.
     * @param operand2 Second operand.
     * @param operand3 Third operand (optional).
     * @return {@code ResponseEntity<ApiResponse<Result>>} representing the response.
     */
    @GetMapping(path = {"multiply/{operand1}/{operand2}", "multiply/{operand1}/{operand2}/{operand3}"})
    public ResponseEntity<ApiResponse<Result>> multiply(@PathVariable BigDecimal operand1,
                                                        @PathVariable BigDecimal operand2,
                                                        @PathVariable(required = false) BigDecimal operand3) {
        return ResponseEntity.ok(
                ApiResponse.<Result>builder()
                        .message(OK)
                        .data(calculatorService.multiply(operand1, operand2, operand3))
                        .build());
    }

    /**
     * Division operation handler.
     *
     * @param dividend Dividend operand.
     * @param divisor Divisor operand.
     * @return {@code ResponseEntity<ApiResponse<Result>>} representing the response.
     */
    @GetMapping(path = "divide/{dividend}/{divisor}")
    public ResponseEntity<ApiResponse<Result>> divide(@PathVariable BigDecimal dividend,
                                                      @PathVariable BigDecimal divisor) {
        return ResponseEntity.ok(
                ApiResponse.<Result>builder()
                        .message(OK)
                        .data(calculatorService.divide(dividend, divisor))
                        .build());
    }
}
