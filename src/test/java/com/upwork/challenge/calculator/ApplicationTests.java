package com.upwork.challenge.calculator;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Integration test suite. It uses <a href="http://rest-assured.io/">Rest-Assured</a> testing library for issuing
 * request and asserting on the responses in a given/when/then fashion. Test methods' names are self-explanatory.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationTests {

    @BeforeClass
    public static void initRestAssured() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setBasePath("/api/v1/calculator")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void shouldAddTwoOperands() {
        when()
            .get("/add/{operand1}/{operand2}", 3.5, 4.2)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("message", is(HttpStatus.OK.getReasonPhrase()))
            .body("data.result", is(7.7F));
    }

    @Test
    public void shouldAddThreeOperands() {
        when()
            .get("/add/{operand1}/{operand2}/{operand3}", 1.5, 2.2, 4)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("message", is(HttpStatus.OK.getReasonPhrase()))
            .body("data.result", is(7.7F));
    }

    @Test
    public void shouldFailOnAdditionOfNonNumericOperands() {
        when()
            .get("/add/{operand1}/{operand2}", 123, "abc")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.size()", is(1))
            .body("errors[0]", is("Failed to convert value of type 'java.lang.String' to required type " +
                    "'java.math.BigDecimal'; nested exception is java.lang.NumberFormatException: Character a is " +
                    "neither a decimal digit number, decimal point, nor \"e\" notation exponential mark."));
    }

    @Test
    public void shouldSubtractTwoOperands() {
        when()
            .get("/subtract/{operand1}/{operand2}", 10, 2.3)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("message", is(HttpStatus.OK.getReasonPhrase()))
            .body("data.result", is(7.7F));
    }

    @Test
    public void shouldSubtractThreeOperands() {
        when()
            .get("/subtract/{operand1}/{operand2}/{operand3}", 10, 2.2, 0.1)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("message", is(HttpStatus.OK.getReasonPhrase()))
            .body("data.result", is(7.7F));
    }

    @Test
    public void shouldFailOnSubtractionOfNonNumericOperands() {
        when()
            .get("/subtract/{operand1}/{operand2}", 123, "abc")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.size()", is(1))
            .body("errors[0]", is("Failed to convert value of type 'java.lang.String' to required type " +
                    "'java.math.BigDecimal'; nested exception is java.lang.NumberFormatException: Character a is " +
                    "neither a decimal digit number, decimal point, nor \"e\" notation exponential mark."));
    }

    @Test
    public void shouldMultiplyTwoOperands() {
        when()
            .get("/multiply/{operand1}/{operand2}", 2, 2)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("message", is(HttpStatus.OK.getReasonPhrase()))
            .body("data.result", is(4));
    }

    @Test
    public void shouldMultiplyThreeOperands() {
        when()
            .get("/multiply/{operand1}/{operand2}/{operand3}", 2, 2, 2)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("message", is(HttpStatus.OK.getReasonPhrase()))
            .body("data.result", is(8));
    }

    @Test
    public void shouldFailOnMultiplicationOfNonNumericOperands() {
        when()
            .get("/multiply/{operand1}/{operand2}", 123, "abc")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.size()", is(1))
            .body("errors[0]", is("Failed to convert value of type 'java.lang.String' to required type " +
                    "'java.math.BigDecimal'; nested exception is java.lang.NumberFormatException: Character a is " +
                    "neither a decimal digit number, decimal point, nor \"e\" notation exponential mark."));
    }

    @Test
    public void shouldDivideDividendByDivisor() {
        when()
            .get("/divide/{dividend}/{divisor}", 14, 2)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("message", is(HttpStatus.OK.getReasonPhrase()))
            .body("data.result", is(7));
    }

    @Test
    public void shouldFailOnDivisionOfNonNumericOperands() {
        when()
            .get("/divide/{operand1}/{operand2}", 123, "abc")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.size()", is(1))
            .body("errors[0]", is("Failed to convert value of type 'java.lang.String' to required type " +
                    "'java.math.BigDecimal'; nested exception is java.lang.NumberFormatException: Character a is " +
                    "neither a decimal digit number, decimal point, nor \"e\" notation exponential mark."));
    }

    @Test
    public void shouldFailOnDivisionByZero() {
        when()
            .get("/divide/{dividend}/{divisor}", 14, 0)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.size()", is(1))
            .body("errors[0]", is("/ by zero"));
    }

    /**
     * Test cache usage by issuing the same request twice and verifying that the first request lasted more than a
     * second, by means of a fixed delay in the service method itself, while the second one lasted less than a few
     * milliseconds. That is because it hits the cache y does not executes the service method body.
     */
    @Test
    public void shouldAddSameTwoOperandsTwiceAndGetCachedResultOnSecondRequest() {
        when()
            .get("/add/{operand1}/{operand2}", 3.5, 4.2)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("message", is(HttpStatus.OK.getReasonPhrase()))
            .body("data.result", is(7.7F))
            .time(greaterThanOrEqualTo(1L), TimeUnit.SECONDS);

        when()
            .get("/add/{operand1}/{operand2}", 3.5, 4.2)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("message", is(HttpStatus.OK.getReasonPhrase()))
            .body("data.result", is(7.7F))
            .time(lessThan(20L), TimeUnit.MILLISECONDS);
    }
}
