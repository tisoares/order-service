package com.tisoares.oderservice.internal.configuration;

import com.tisoares.oderservice.internal.configuration.resolver.PageableResolver;
import com.tisoares.oderservice.internal.configuration.resolver.SearchCriteriaResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageableResolver());
        resolvers.add(new SearchCriteriaResolver());
    }
}
