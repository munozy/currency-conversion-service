package org.munozy.microservices.demo.currencyconversionservice.controller;

import org.munozy.microservices.demo.currencyconversionservice.dto.ConversionDto;
import org.munozy.microservices.demo.currencyconversionservice.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(ConversionController.PATH)
public class ConversionController {
    static final String PATH = "/conversions";

    @Autowired
    CurrencyConversionService conversionService;

    @GetMapping(path = "/from/{from}/to/{to}/amount/{amount}")
    public ConversionDto getConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal amount) {
        return conversionService.getConversionWithFeign(from, to, amount);
    }

    @GetMapping(path = "/v2/from/{from}/to/{to}/amount/{amount}")
    public ConversionDto getConversionV2(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal amount) {
        return conversionService.getConversionWithRetrofit(from, to, amount);
    }

    @GetMapping(path = "/v3/from/{from}/to/{to}/amount/{amount}")
    public ConversionDto getConversionV3(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal amount) {
        return conversionService.getConversionWithRestTemplate(from, to, amount);
    }
}
