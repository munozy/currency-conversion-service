package org.munozy.microservices.demo.currencyconversionservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExchangeDto {

    String from;

    String to;

    BigDecimal conversion;

    int port;
}
