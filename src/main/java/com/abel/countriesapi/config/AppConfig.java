package com.abel.countriesapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    @Bean
    public Docket swaggerPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()

                .apis(RequestHandlerSelectors.basePackage("com.abel.countriesapi.controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(apiInfo())
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()) )
                .securitySchemes(Arrays.asList(apiKey()));

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "CountriesAPI - Klasha",
                "Countries-API",
                "1.0",
                "",
                new Contact("Abel Agbachi", "", "Abel.Agbachi@gmail.com"), "License of API", "API license URL", Collections.emptyList());
    }

    private SecurityContext securityContext(){ //pass a list of security references to the sec context builder and build
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth(){//returns a list of security references
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");//create an AuthorizationScope
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];//creat an array of AuthorizationScope
        authorizationScopes[0] = authorizationScope;//add AuthorizationScope to AuthorizationScope[]
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));//construct a security reference with a String and a AuthorizationScope[]
    }

}
