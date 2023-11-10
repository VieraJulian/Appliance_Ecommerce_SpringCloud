package com.ApplianceEcommerce.products.controller;

import com.ApplianceEcommerce.products.dto.ProductDTO;
import com.ApplianceEcommerce.products.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService prodServ;

    @Value("${server.port}")
    private int port;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        try {
            List<ProductDTO> listProducts = prodServ.getProducts();

            return new ResponseEntity<>(listProducts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        try {
            System.out.println("-------------------------------- port " + port);
            ProductDTO product = prodServ.getProduct(id);

            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getProductByName(@RequestParam String name) {
        try {
            List<ProductDTO> listProducts = prodServ.getProductByName(name);

            return new ResponseEntity<>(listProducts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        try {
            ProductDTO productNew = prodServ.createProduct(product);

            return new ResponseEntity<>(productNew, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
        try {
            ProductDTO productUpdated = prodServ.updateProduct(id, product);

            return new ResponseEntity<>(productUpdated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            prodServ.deleteProduct(id);

            return new ResponseEntity<>("¡Product deleted success!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
