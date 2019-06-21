package com.upwork.challenge.calculator;

import com.upwork.challenge.calculator.core.CalculatorService;
import com.upwork.challenge.calculator.support.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Calculator service unit test suite. Test methods' names are self-explanatory.
 */
@RunWith(SpringRunner.class)
public class CalculatorServiceTests {

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public CalculatorService calculatorService() {
            return new CalculatorService();
        }
    }

    @Autowired
    private CalculatorService calculatorService;

    @Test
    public void shouldAddTwoOperands() {
        Result result = calculatorService.add(BigDecimal.valueOf(3.5), BigDecimal.valueOf(4.2));

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(7.7));
    }

    @Test
    public void shouldAddThreeOperands() {
        Result result = calculatorService.add(BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.2), BigDecimal.valueOf(4));

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(7.7));
    }

    @Test
    public void shouldAddOperandsWithNullOperand() {
        Result result = calculatorService.add(BigDecimal.valueOf(3.5), BigDecimal.valueOf(4.2), null);

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(7.7));
    }

    @Test
    public void shouldReturnEmptyResultOnAdditionOfNoOperands() {
        Result result = calculatorService.add();

        assertThat(result).isEqualTo(Result.EMPTY);
    }

    @Test
    public void shouldSubtractTwoOperands() {
        Result result = calculatorService.subtract(BigDecimal.TEN, BigDecimal.valueOf(2.3));

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(7.7));
    }

    @Test
    public void shouldSubtractThreeOperands() {
        Result result = calculatorService.subtract(BigDecimal.TEN, BigDecimal.valueOf(2.2), BigDecimal.valueOf(0.1));

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(7.7));
    }

    @Test
    public void shouldSubtractOperandsWithNullOperand() {
        Result result = calculatorService.subtract(BigDecimal.TEN, BigDecimal.valueOf(2.3), null);

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(7.7));
    }

    @Test
    public void shouldReturnEmptyResultOnSubtractionOfNoOperands() {
        Result result = calculatorService.subtract();

        assertThat(result).isEqualTo(Result.EMPTY);
    }

    @Test
    public void shouldMultiplyTwoOperands() {
        Result result = calculatorService.multiply(BigDecimal.valueOf(2), BigDecimal.valueOf(2));

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(4));
    }

    @Test
    public void shouldMultiplyThreeOperands() {
        Result result = calculatorService.multiply(BigDecimal.valueOf(2), BigDecimal.valueOf(2), BigDecimal.valueOf(2));

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(8));
    }

    @Test
    public void shouldMultiplyOperandsWithNullOperand() {
        Result result = calculatorService.multiply(BigDecimal.valueOf(2), BigDecimal.valueOf(2), null);

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(4));
    }

    @Test
    public void shouldReturnEmptyResultOnMultiplicationOfNoOperands() {
        Result result = calculatorService.multiply();

        assertThat(result).isEqualTo(Result.EMPTY);
    }

    @Test
    public void shouldDivideDividendByDivisor() {
        Result result = calculatorService.divide(BigDecimal.valueOf(14), BigDecimal.valueOf(2));

        assertThat(result.getValue()).isEqualTo(BigDecimal.valueOf(7));
    }

    @Test
    public void shouldThrowArithmeticExceptionOnDivisionByZero() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> calculatorService.divide(BigDecimal.valueOf(14), BigDecimal.valueOf(0)))
                .withMessage("/ by zero");
    }

    @Test
    public void shouldThrowNullPointerExceptionOnDivisionWithNullDividend() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> calculatorService.divide(null, BigDecimal.valueOf(2)))
                .withMessage("Dividend should not be null");
    }

    @Test
    public void shouldThrowNullPointerExceptionOnDivisionWithNullDivisor() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> calculatorService.divide(BigDecimal.valueOf(14), null))
                .withMessage("Divisor should not be null");
    }
}
