package org.munozy.microservices.demo.currencyconversionservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversionDto {

    String from;

    String to;

    BigDecimal amount;

    BigDecimal conversion;

    BigDecimal convertedAmount;

}
