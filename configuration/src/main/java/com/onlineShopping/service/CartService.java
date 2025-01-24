package com.onlineShopping.service;

import com.onlineShopping.model.Cart;

import java.util.List;

public interface CartService {
    public Cart saveCart(Long productId, Long userId);

    public List<Cart> getCartByUser(Long userId);

    public Integer getCountCart(Long userId);

    public void updateQuantity(String sy, Long cartId);

}
