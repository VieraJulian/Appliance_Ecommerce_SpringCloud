package com.ApplianceEcommerce.products.service;

import com.ApplianceEcommerce.products.dto.ProductDTO;

import java.util.List;

public interface IProductService {

    public List<ProductDTO> getProducts();

    public ProductDTO getProduct(Long id);

    public List<ProductDTO> getProductByName(String name);

    public ProductDTO createProduct(ProductDTO product);

    public ProductDTO updateProduct(Long id, ProductDTO product);

    public void deleteProduct(Long id);

}
