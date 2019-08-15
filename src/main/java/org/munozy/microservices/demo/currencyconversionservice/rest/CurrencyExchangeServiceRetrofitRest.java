package org.munozy.microservices.demo.currencyconversionservice.rest;

import org.munozy.microservices.demo.currencyconversionservice.dto.ExchangeDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyExchangeServiceRetrofitRest {

    @GET("currency-exchange/from/{from}/to/{to}")
    Call<ExchangeDto> getExchange(@Path("from") String from,
                                  @Path("to") String to);
}
