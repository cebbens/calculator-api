package com.upwork.challenge.calculator.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Swagger configuration for API documentation generation.
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Collections.singleton("application/json"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.upwork.challenge.calculator.core"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo("Calculator API",
                        "REST API Specification",
                        "v1",
                        null,
                        new Contact("Cristian Ebbens", null, "cebbens@gmail.com"),
                        null, null, Collections.emptyList()));
    }
}