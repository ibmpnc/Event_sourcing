package com.example.bank.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

	
	
	  @Bean
	    public Docket apiDocket() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors
	                        .basePackage("com.example.bank"))
	                .paths(PathSelectors.any())
	                .build()
	                .apiInfo(getApiInfo());
	    }

	    private ApiInfo getApiInfo() {
	        return new ApiInfo(
	                "Bank Account sample code using event sourcing and CQRS",
	                "This app to demonstrate Event sourcing and part of the techical test for IBM ",
	                "0.0.1-SNAPSHOT",
	                "Terms of Service",
	                new Contact("Wassim Abuzahra",
	                        "  ",
	                        "wass50@hotmail.com"),
	                "",
	                "",
	                Collections.emptyList());
	    }
}
