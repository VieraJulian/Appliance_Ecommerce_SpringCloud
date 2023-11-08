package com.ApplianceEcommerce.carts.repository;

import com.ApplianceEcommerce.carts.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
}
