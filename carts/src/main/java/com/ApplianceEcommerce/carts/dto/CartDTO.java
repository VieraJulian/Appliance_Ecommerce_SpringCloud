package com.ApplianceEcommerce.carts.dto;

import com.ApplianceEcommerce.carts.model.CartProduct;
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
