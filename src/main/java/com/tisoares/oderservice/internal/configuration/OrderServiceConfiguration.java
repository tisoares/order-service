package com.tisoares.oderservice.internal.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@ComponentScan(OrderServiceConstants.BASE_PACKAGE)
@EnableJpaRepositories(value = OrderServiceConstants.BASE_PACKAGE)
@EnableJpaAuditing
@EnableConfigurationProperties(OrderServiceProperties.class)
//@ConfigurationProperties(prefix = "spring.liquibase", ignoreUnknownFields = false)
@EnableSwagger2
public class OrderServiceConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tisoares.oderservice.external"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfoMetaData())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(basicAuthScheme()));
    }

    private ApiInfo apiInfoMetaData() {

        return new ApiInfoBuilder().title("Order Service")
                .description("Order Service Application")
                .contact(new Contact("Tiago Soares", "https://github.com/tisoares", "tisoare@outlook.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(basicAuthReference()))
                .forPaths(PathSelectors.any())
                .build();
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }

}
