package com.ApplianceEcommerce.carts.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductDTO {

    private Long id;
    private int quantity;
    private ProductDTO product;
}
