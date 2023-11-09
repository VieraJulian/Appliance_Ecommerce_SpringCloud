package com.ApplianceEcommerce.sales.repository;

import com.ApplianceEcommerce.sales.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cartApi", url = "http://localhost:8003/carts")
public interface CartAPI {

    @GetMapping("/detail/{id}")
    public CartDTO getCart(@PathVariable Long id);

    @PostMapping("/{cart_id}/clear")
    public void clearCart(@PathVariable Long cart_id);
}
