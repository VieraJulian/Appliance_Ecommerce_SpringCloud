package com.ApplianceEcommerce.sales.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "products-service")
public interface IProductAPI {

    @PutMapping("/products/{product_id}/stock")
    public void reduceStock(@PathVariable Long product_id, @RequestParam Integer quantity);

}
