package com.tisoares.oderservice.internal.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = OrderServiceConstants.APPLICATION_PROPERTY_PREFIX)
public class OrderServiceProperties {
}
