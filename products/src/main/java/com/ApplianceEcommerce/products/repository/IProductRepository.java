package com.ApplianceEcommerce.products.repository;

import com.ApplianceEcommerce.products.dto.ProductDTO;
import com.ApplianceEcommerce.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    public List<Product> getProductByName(String name);
}
