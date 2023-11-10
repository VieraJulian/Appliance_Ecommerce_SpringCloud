package com.ApplianceEcommerce.carts.repository;

import com.ApplianceEcommerce.carts.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products-service")
public interface ProductAPI {

    @GetMapping("products/details/{id}")
    public ProductDTO getProduct(@PathVariable Long id);
}
