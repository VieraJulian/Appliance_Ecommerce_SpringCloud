package com.ApplianceEcommerce.carts.service;

import com.ApplianceEcommerce.carts.dto.CartDTO;
import com.ApplianceEcommerce.carts.dto.CartProductDTO;
import com.ApplianceEcommerce.carts.dto.ProductDTO;
import com.ApplianceEcommerce.carts.model.Cart;
import com.ApplianceEcommerce.carts.model.CartProduct;
import com.ApplianceEcommerce.carts.repository.ICartProductRepository;
import com.ApplianceEcommerce.carts.repository.ICartRepository;
import com.ApplianceEcommerce.carts.repository.ProductAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartService implements ICartService {
    @Autowired
    private ICartRepository cartRepo;

    @Autowired
    private ICartProductRepository cartProductRepo;

    @Autowired
    private ProductAPI productAPI;

    @Override
    public CartDTO getCart(Long id) {
        Cart cart = cartRepo.findById(id).orElse(null);

        if (cart == null) {
            return null;
        }

        List<CartProduct> listProductDB = cart.getListProducts();
        List<CartProductDTO> listProduct = new ArrayList<>();

        for (CartProduct cp : listProductDB) {
            ProductDTO prod = productAPI.getProduct(cp.getProduct_id());

            listProduct.add(CartProductDTO.builder()
                            .id(cp.getId())
                            .quantity(cp.getQuantity())
                            .product(prod)
                    .build());
        }

        return CartDTO.builder()
                        .id(cart.getId())
                        .total(cart.getTotal())
                        .listProducts(listProduct)
                        .build();
    }

    @Override
    public Long createCart() {
        Cart cart = cartRepo.save(Cart.builder()
                    .total(new BigDecimal(0))
                    .build());

        return cart.getId();
    }

    @Override
    public CartDTO addProductToCart(Long cart_id, Long product_id) {
        Cart cart = cartRepo.findById(cart_id).orElse(null);

        if (cart == null) {
            return null;
        }

        boolean found = false;

        for (CartProduct cp : cart.getListProducts()) {

            if (cp.getProduct_id() == product_id) {
                ProductDTO prod = productAPI.getProduct(cp.getProduct_id());

                if (prod.getStock() > cp.getQuantity()){
                    cart.setTotal(cart.getTotal().add(prod.getPrice()));
                    cp.setQuantity(cp.getQuantity() + 1);
                    cp.setCart(cart);
                }

                found = true;
                break;
            }
        }

        if (!found) {
            ProductDTO prod = productAPI.getProduct(product_id);

            if (prod.getStock() > 0) {
                cart.setTotal(cart.getTotal().add(prod.getPrice()));

                cart.getListProducts().add(CartProduct.builder()
                        .product_id(product_id)
                        .quantity(1)
                        .cart(cart)
                        .build());
            }
        }

        Cart cartUpdated = cartRepo.save(cart);

        List<CartProductDTO> listProducts = new ArrayList<>();

        for (CartProduct cp : cart.getListProducts()) {
            ProductDTO prod = productAPI.getProduct(cp.getProduct_id());

            listProducts.add(CartProductDTO.builder()
                            .id(cp.getId())
                            .quantity(cp.getQuantity())
                            .product(prod)
                            .build());
        }

        return CartDTO.builder()
                .id(cartUpdated.getId())
                .total(cartUpdated.getTotal())
                .listProducts(listProducts)
                .build();
    }

    @Override
    public CartDTO subtractProductFromCart(Long cart_id, Long product_id) {
        Cart cart = cartRepo.findById(cart_id).orElse(null);

        if (cart == null) {
            return null;
        }

        for (CartProduct cp : cart.getListProducts()) {

            if (cp.getProduct_id() == product_id) {
                ProductDTO prod = productAPI.getProduct(cp.getProduct_id());

                cart.setTotal(cart.getTotal().subtract(prod.getPrice()));

                if (cp.getQuantity() > 1) {
                    cp.setQuantity(cp.getQuantity() - 1);
                } else if (cp.getQuantity() == 1) {
                    cart.getListProducts().remove(cp);
                    cartProductRepo.deleteById(cp.getId());
                    cart = cartRepo.findById(cart_id).orElse(null);
                }

                break;
            }
        }

        Cart cartUpdated = cartRepo.save(cart);

        List<CartProductDTO> listProducts = new ArrayList<>();

        for (CartProduct cp : cart.getListProducts()) {
            ProductDTO prod = productAPI.getProduct(cp.getProduct_id());

            listProducts.add(CartProductDTO.builder()
                    .id(cp.getId())
                    .quantity(cp.getQuantity())
                    .product(prod)
                    .build());
        }

        return CartDTO.builder()
                .id(cartUpdated.getId())
                .total(cartUpdated.getTotal())
                .listProducts(listProducts)
                .build();
    }

    @Override
    public CartDTO removeProductFromCart(Long cart_id, Long product_id) {
        Cart cart = cartRepo.findById(cart_id).orElse(null);

        if (cart == null) {
            return null;
        }

        for (CartProduct cp : cart.getListProducts()) {

            if (cp.getProduct_id() == product_id) {
                ProductDTO prod = productAPI.getProduct(cp.getProduct_id());
                int quantity = cp.getQuantity();

                cart.setTotal(cart.getTotal().subtract(prod.getPrice().multiply(new BigDecimal(quantity))));

                cart.getListProducts().remove(cp);
                cartProductRepo.deleteById(cp.getId());
                cart = cartRepo.findById(cart_id).orElse(null);

                break;
            }
        }

        Cart cartUpdated = cartRepo.save(cart);

        List<CartProductDTO> listProducts = new ArrayList<>();

        for (CartProduct cp : cart.getListProducts()) {
            ProductDTO prod = productAPI.getProduct(cp.getProduct_id());

            listProducts.add(CartProductDTO.builder()
                    .id(cp.getId())
                    .quantity(cp.getQuantity())
                    .product(prod)
                    .build());
        }

        return CartDTO.builder()
                .id(cartUpdated.getId())
                .total(cartUpdated.getTotal())
                .listProducts(listProducts)
                .build();
    }

    @Override
    public void clearCart(Long cart_id) {
        Cart cart = cartRepo.findById(cart_id).orElse(null);

        if (cart != null) {
            cart.setTotal(new BigDecimal("0"));

            for (CartProduct cp : cart.getListProducts()) {
                cartProductRepo.deleteById(cp.getId());
            }

            cart.getListProducts().clear();

            cartRepo.save(cart);
        }
    }

    @Override
    public void deleteCart(Long id) {
        cartRepo.deleteById(id);
    }
}
