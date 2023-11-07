package com.ApplianceEcommerce.products.service;

import com.ApplianceEcommerce.products.dto.ProductDTO;
import com.ApplianceEcommerce.products.model.Product;
import com.ApplianceEcommerce.products.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository prodRepo;

    @Override
    public List<ProductDTO> getProducts() {
        List<Product> listProductsDB = prodRepo.findAll();
        List<ProductDTO> listProducts = new ArrayList<>();

        for (Product prod : listProductsDB) {
            listProducts.add(
                    ProductDTO.builder()
                            .id(prod.getId())
                            .name(prod.getName())
                            .price(prod.getPrice())
                            .brand(prod.getBrand())
                            .stock(prod.getStock())
                            .build()
            );
        }

        return listProducts;
    }

    @Override
    public ProductDTO getProduct(Long id) {
        Product productDB = prodRepo.findById(id).orElse(null);

        if (productDB == null) {
            return null;
        }

        return ProductDTO.builder()
                .id(productDB.getId())
                .name(productDB.getName())
                .price(productDB.getPrice())
                .brand(productDB.getBrand())
                .stock(productDB.getStock())
                .build();

    }

    @Override
    public List<ProductDTO> getProductByName(String name) {
        List<Product> listProductsDB = prodRepo.getProductByName(name);
        List<ProductDTO> listProducts = new ArrayList<>();

        for (Product prod : listProductsDB) {
            listProducts.add(
                    ProductDTO.builder()
                            .id(prod.getId())
                            .name(prod.getName())
                            .price(prod.getPrice())
                            .brand(prod.getBrand())
                            .stock(prod.getStock())
                            .build()
            );
        }

        return listProducts;
    }

    @Override
    public ProductDTO createProduct(ProductDTO product) {
        Product productNew = Product.builder()
                                .name(product.getName())
                                .price(product.getPrice())
                                .brand(product.getBrand())
                                .stock(product.getStock())
                                .build();

        prodRepo.save(productNew);

        return ProductDTO.builder()
                .id(productNew.getId())
                .name(productNew.getName())
                .price(productNew.getPrice())
                .brand(productNew.getBrand())
                .stock(productNew.getStock())
                .build();
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO product) {
        Product productDB = prodRepo.findById(id).orElse(null);

        if (productDB == null) {
            return null;
        }

        productDB.setName(product.getName());
        productDB.setPrice(product.getPrice());
        productDB.setBrand(product.getBrand());
        productDB.setStock(product.getStock());

        prodRepo.save(productDB);

        return ProductDTO.builder()
                    .name(productDB.getName())
                    .price(productDB.getPrice())
                    .brand(productDB.getBrand())
                    .stock(productDB.getStock())
                    .build();
    }

    @Override
    public void deleteProduct(Long id) {
        prodRepo.deleteById(id);
    }
}
