package org.munozy.microservices.demo.currencyconversionservice.rest;

import org.munozy.microservices.demo.currencyconversionservice.dto.ExchangeDto;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 *
 * With Ribbon, it is not more necessary to define url (see application.yml)
 * @FeignClient(name = "currency-exchange-service", url = "localhost:8000")
 */

@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceFeignRest {

    @GetMapping(path = "/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    ExchangeDto getExchange(@PathVariable String from, @PathVariable String to);
}
