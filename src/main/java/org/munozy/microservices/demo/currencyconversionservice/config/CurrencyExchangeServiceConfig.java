package org.munozy.microservices.demo.currencyconversionservice.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CurrencyExchangeServiceConfig {

    @Autowired
    IClientConfig ribbonClientConfig;

    @Bean
    public IRule loadBlancingRule() {
        return new RoundRobinRule();
    }

    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }

    @Bean
    public ServerList<Server> ribbonServerList() {
        return new ConfigurationBasedServerList();
    }
}
