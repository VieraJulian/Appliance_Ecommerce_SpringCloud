package com.ApplianceEcommerce.carts.controller;

import com.ApplianceEcommerce.carts.dto.CartDTO;
import com.ApplianceEcommerce.carts.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private ICartService cartServ;

    @GetMapping("/detail/{id}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long id) {
        try {
            CartDTO cart = cartServ.getCart(id);

            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCart() {
        try {
            Long cart_id = cartServ.createCart();

            return new ResponseEntity<>("¡Cart successfully created with id " + cart_id + "!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{cart_id}/product/{product_id}/add")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long cart_id, @PathVariable Long product_id) {
        try {
            CartDTO cart = cartServ.addProductToCart(cart_id, product_id);

            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{cart_id}/product/{product_id}/subtract")
    public ResponseEntity<CartDTO> subtractProductFromCart(@PathVariable Long cart_id, @PathVariable Long product_id) {
        try {
            CartDTO cart = cartServ.subtractProductFromCart(cart_id, product_id);

            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{cart_id}/product/{product_id}/remove")
    public ResponseEntity<CartDTO> removeProductFromCart(@PathVariable Long cart_id, @PathVariable Long product_id) {
        try {
            CartDTO cart = cartServ.removeProductFromCart(cart_id, product_id);

            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{cart_id}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long cart_id) {
        try {
            cartServ.clearCart(cart_id);

            return new ResponseEntity<>("¡Cart emptied correctly!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cart_id}/delete")
    public ResponseEntity<String> deleteCart(@PathVariable Long cart_id) {
        try {
            cartServ.deleteCart(cart_id);

            return new ResponseEntity<>("¡Cart successfully deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
