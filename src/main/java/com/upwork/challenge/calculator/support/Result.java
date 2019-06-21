package com.upwork.challenge.calculator.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents the result of each a calculation.
 */
@Data
public class Result {

    /**
     * Represents an empty result to be used when a calculation did not return a result.
     */
    public static final Result EMPTY = new Result(null);

    @JsonProperty("result")
    private final BigDecimal value;
}
