package com.tisoares.oderservice.internal.configuration;

public final class OrderServiceConstants {

    public static final String BASE_PACKAGE = "com.tisoares.oderservice";
    public static final String EMAIL_NAME = "Order Service";
    public static final String APPLICATION_PROPERTY_PREFIX = "order-service";
    public static final String URL_PREFIX = "/api";
    public static final String V1 = "/v1";
    public static final String URL_PREFIX_V1 = URL_PREFIX + V1 + "/";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_SEQUENCE_NAME = "default_gen";
    public static final String PAGEABLE_PAGE = "$page";
    public static final String PAGEABLE_SIZE = "$size";
    public static final String PAGEABLE_SORT = "$sort";
    public static final String FILTER_PARAMS = "$filter";
    public static final String FILTER_PARAMS_CONDITIONS_DELIMITER = ";";
    public static final String FILTER_PARAMS_PROPERTIES_DELIMITER = ",";
    public static final String EXPAND_PARAMS = "$expand";
    public static final String EXPAND_PARAMS_DELIMITER = ",";

    public static final int DEFAULT_PAGEABLE_PAGE = 0;
    public static final int DEFAULT_PAGEABLE_SIZE = 50;
    public static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // H2
//            "/h2-console/**",
            // Actuator
            "/actuator/**"
    };

    public static final Integer MAX_EMAIL_ATTEMPTS = 3;

    public static final String ORDER_SUBJECT = "Order ID: %d is completed";

    private OrderServiceConstants() {
    }
}
