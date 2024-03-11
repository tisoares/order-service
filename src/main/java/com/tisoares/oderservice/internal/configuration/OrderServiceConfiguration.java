package com.tisoares.oderservice.internal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.tisoares.oderservice.internal.configuration.OrderServiceConstants.AUTH_WHITELIST;

@Configuration
@EnableSwagger2
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@EnableScheduling
public class OrderServiceConfiguration {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CustomBasicAuthFilter customBasicAuthFilter) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
//                .httpBasic();
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(customBasicAuthFilter, BasicAuthenticationFilter.class);

        http.headers().frameOptions().disable(); //H2 Console
        return http.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("1234")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

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
