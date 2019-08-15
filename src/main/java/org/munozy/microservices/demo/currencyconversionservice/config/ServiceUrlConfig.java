package org.munozy.microservices.demo.currencyconversionservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix="service.url")
public class ServiceUrlConfig {

    private String exchange;
}
