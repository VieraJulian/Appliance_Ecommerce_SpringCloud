package com.ApplianceEcommerce.sales.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDTO {

    private Long id;
    private String operationCode;
    private LocalDate date;
    private BigDecimal total;
}
