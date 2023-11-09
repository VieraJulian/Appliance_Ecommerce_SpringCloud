package com.ApplianceEcommerce.sales.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {

    private Long id;
    private BigDecimal total;
    private List<CartProductDTO> listProducts;
}
