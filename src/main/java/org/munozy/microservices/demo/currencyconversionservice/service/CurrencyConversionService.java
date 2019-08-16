package org.munozy.microservices.demo.currencyconversionservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.munozy.microservices.demo.currencyconversionservice.config.ServiceUrlConfig;
import org.munozy.microservices.demo.currencyconversionservice.dto.ConversionDto;
import org.munozy.microservices.demo.currencyconversionservice.dto.ExchangeDto;
import org.munozy.microservices.demo.currencyconversionservice.rest.CurrencyExchangeServiceFeignRest;
import org.munozy.microservices.demo.currencyconversionservice.rest.CurrencyExchangeServiceRetrofitRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyConversionService {

    static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionService.class);

    final ServiceUrlConfig serviceUrlConfig;

    final CurrencyExchangeServiceRetrofitRest currencyExchangeServiceRetrofitRest;

    final CurrencyExchangeServiceFeignRest currencyExchangeServiceFeignRest;

    public ConversionDto getConversionWithFeign(String from, String to, BigDecimal amount) {
        return calculateConversion(currencyExchangeServiceFeignRest.getExchange(from, to), amount);
    }

    public ConversionDto getConversionWithRetrofit(String from, String to, BigDecimal amount) {
        try {
            Response<ExchangeDto> response =  currencyExchangeServiceRetrofitRest.getExchange(from, to).execute();
            if (!response.isSuccessful()) {
                verifyContent(response);
            }
            return calculateConversion(response.body(), amount);
        } catch (IOException e) {
            LOGGER.error(String.format("%d Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
        return ConversionDto.builder().build();
    }

    public ConversionDto getConversionWithRestTemplate(String from, String to, BigDecimal amount) {
        Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<ExchangeDto> responseEntity = new RestTemplate().getForEntity(
                serviceUrlConfig.getExchange() + "currency-exchange/from/{from}/to/{to}",
                ExchangeDto.class,
                uriVariables
        );

        return calculateConversion(responseEntity.getBody(), amount);
    }

    private ConversionDto calculateConversion(ExchangeDto exchangeDto, BigDecimal amount) {
        return ConversionDto.builder()
                .from(exchangeDto.getFrom())
                .to(exchangeDto.getTo())
                .conversion(exchangeDto.getConversion())
                .port(exchangeDto.getPort())
                .amount(amount)
                .convertedAmount(amount.multiply(exchangeDto.getConversion()))
                .build();
    }

    private void verifyContent(Response<ExchangeDto> response) {
        String contentType = response.headers().get(HttpHeaders.CONTENT_TYPE);
        if (contentType != null && MediaType.APPLICATION_JSON.includes(MediaType.parseMediaType(contentType))) {
            LOGGER.error(response.errorBody().toString());
        }
    }
}
