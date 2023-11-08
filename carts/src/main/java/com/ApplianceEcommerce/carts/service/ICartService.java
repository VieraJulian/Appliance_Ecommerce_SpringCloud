package com.ApplianceEcommerce.carts.service;

import com.ApplianceEcommerce.carts.dto.CartDTO;

public interface ICartService {

    public CartDTO getCart(Long id);

    public Long createCart();

    public CartDTO addProductToCart(Long cart_id, Long product_id);

    public CartDTO subtractProductFromCart(Long cart_id, Long product_id);

    public CartDTO removeProductFromCart(Long cart_id, Long product_id);

    public void clearCart(Long cart_id);

    public void deleteCart(Long id);
}
