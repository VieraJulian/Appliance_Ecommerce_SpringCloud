package com.ApplianceEcommerce.carts.repository;

import com.ApplianceEcommerce.carts.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartProductRepository extends JpaRepository<CartProduct, Long> {
}
