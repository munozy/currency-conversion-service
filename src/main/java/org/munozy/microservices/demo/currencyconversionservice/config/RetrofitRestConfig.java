package org.munozy.microservices.demo.currencyconversionservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okhttp3.OkHttpClient;
import org.munozy.microservices.demo.currencyconversionservice.rest.CurrencyExchangeServiceRetrofitRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RetrofitRestConfig {

    static final ObjectMapper MAPPER = new ObjectMapper();

    final ServiceUrlConfig serviceUrlConfig;

    @Bean
    public CurrencyExchangeServiceRetrofitRest currencyExchangeServiceRetrofitRest () {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serviceUrlConfig.getExchange())
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(JacksonConverterFactory.create(MAPPER))
                .build();

        return retrofit.create(CurrencyExchangeServiceRetrofitRest.class);
    }
}
