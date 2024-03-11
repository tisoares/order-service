package com.tisoares.oderservice.internal.configuration;

import com.tisoares.oderservice.internal.configuration.resolver.PageableResolver;
import com.tisoares.oderservice.internal.configuration.resolver.SearchCriteriaResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AutoConfiguration
@EnableJpaRepositories
@EnableJpaAuditing
@EnableConfigurationProperties(OrderServiceProperties.class)
@ConfigurationProperties(prefix = "spring.liquibase", ignoreUnknownFields = false)
public class ArgumentResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageableResolver());
        resolvers.add(new SearchCriteriaResolver());
    }
}
