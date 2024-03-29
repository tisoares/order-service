package com.tisoares.oderservice.internal.configuration.resolver;

import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.BaseEntity;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SearchCriteriaResolver implements HandlerMethodArgumentResolver {

    private static final String CONTROLLER_SUFFIX = "ControllerImpl";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(SearchCriteria.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> clazz = Class.forName(OrderServiceConstants.DOMAIN_PACKAGE + "." + getNameClass(parameter));
        logger.info("Class request: {}", clazz.getSimpleName());
        List<String> expands = new ArrayList<>();
        List<SearchCriteria.Filter> filters = new ArrayList<>();
        if (webRequest.getParameterMap().containsKey(OrderServiceConstants.EXPAND_PARAMS)) {
            expands = Arrays.stream(webRequest.getParameterMap().get(OrderServiceConstants.EXPAND_PARAMS))
                    .flatMap(s -> Arrays.stream(s.split(OrderServiceConstants.EXPAND_PARAMS_DELIMITER)))
                    .collect(Collectors.toList());
        }
        if (webRequest.getParameterMap().containsKey(OrderServiceConstants.FILTER_PARAMS)) {
            filters = Arrays.stream(webRequest.getParameterMap().get(OrderServiceConstants.FILTER_PARAMS))
                    .flatMap(s -> Arrays.stream(s.split(OrderServiceConstants.FILTER_PARAMS_CONDITIONS_DELIMITER)))
                    .map(s -> {
                        String[] f = s.split(OrderServiceConstants.FILTER_PARAMS_PROPERTIES_DELIMITER);
                        if (f.length == 3) {
                            return SearchCriteria.Filter.builder()
                                    .field(f[0].trim())
                                    .operator(SearchCriteria.Filter.QueryOperator.valueOf(f[1].trim().toUpperCase()))
                                    .value(f[2].trim())
                                    .build();
                        } else {
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        }
        return SearchCriteria.builder()
                .expands(expands)
                .filters(filters)
                .clazz((Class<BaseEntity>) clazz)
                .build();
    }

    private String getNameClass(MethodParameter parameter) {
        String request = parameter.getDeclaringClass().getSimpleName();
        if (request.endsWith(CONTROLLER_SUFFIX)) {
            return request.substring(0, request.indexOf(CONTROLLER_SUFFIX));
        }
        return "";
    }

}
