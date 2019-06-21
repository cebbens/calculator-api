package com.upwork.challenge.calculator.core;

import com.upwork.challenge.calculator.support.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Service which calculates several math operations. It caches the result of every successful calculation taking into
 * account its operands' values, resulting in a significant gain in performance. It uses a simple cache strategy,
 * Spring's default implementation, {@link org.springframework.cache.concurrent.ConcurrentMapCache}, which
 * implements Spring's own {@link org.springframework.cache.Cache} abstraction.
 */
@Slf4j
@Cacheable("calculator")
@Service
public class CalculatorService {

    /**
     * Addition operation.
     *
     * @param operands Addition operands. It supports any number of operands. and {@code null} values are filtered.
     * @return {@link Result} of the operation. If a result cannot be calculated, a {@link Result#EMPTY} is returned.
     */
    public Result add(BigDecimal... operands) {
        log.info(String.format("Add [operands: %s]...", Arrays.toString(operands)));

        // Testing purposes
        simulateExpensiveCalculation();

        return Arrays.stream(operands)
                .filter(Objects::nonNull)
                .reduce(BigDecimal::add)
                .map(Result::new)
                .orElse(Result.EMPTY);
    }

    /**
     * Subtraction operation.
     *
     * @param operands Subtraction operands. It supports any number of operands, and {@code null} values are filtered.
     * @return {@link Result} of the operation. If a result cannot be calculated, a {@link Result#EMPTY} is returned.
     */
    public Result subtract(BigDecimal... operands) {
        log.info(String.format("Subtract [operands: %s]...", Arrays.toString(operands)));

        return Arrays.stream(operands)
                .filter(Objects::nonNull)
                .reduce(BigDecimal::subtract)
                .map(Result::new)
                .orElse(Result.EMPTY);
    }

    /**
     * Multiplication operation.
     *
     * @param operands Multiplication operands. It supports any number of operands. and {@code null} values are filtered.
     * @return {@link Result} of the operation. If a result cannot be calculated, a {@link Result#EMPTY} is returned.
     */
    public Result multiply(BigDecimal... operands) {
        log.info(String.format("Multiply [operands: %s]...", Arrays.toString(operands)));

        return Arrays.stream(operands)
                .filter(Objects::nonNull)
                .reduce(BigDecimal::multiply)
                .map(Result::new)
                .orElse(Result.EMPTY);
    }

    /**
     * Division operation.
     *
     * @param dividend Division dividend. If it is {@code null}, it will throw a {@link NullPointerException}.
     * @param divisor Division divisor. If it is {@code null}, it will throw a {@link NullPointerException}.
     *                If it is equal to zero, it will throw a {@link ArithmeticException}.
     * @return {@link Result} of the operation. If a result cannot be calculated, a {@link Result#EMPTY} is returned.
     */
    public Result divide(BigDecimal dividend, BigDecimal divisor) {
        log.info(String.format("Divide [dividend: %s, divisor: %s]...", dividend, divisor));

        Objects.requireNonNull(dividend, "Dividend should not be null");
        Objects.requireNonNull(divisor, "Divisor should not be null");

        return Optional.of(dividend.divide(divisor, BigDecimal.ROUND_HALF_DOWN))
                .map(Result::new)
                .orElse(Result.EMPTY);
    }

    /**
     * Simulates an expensive operation. Used for testing purposes for noticing the cache performance gain.
     */
    @SneakyThrows
    private void simulateExpensiveCalculation() {
        TimeUnit.SECONDS.sleep(1);
    }
}
