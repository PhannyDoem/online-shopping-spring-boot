package com.onlineShopping.service.impl;

import com.onlineShopping.model.Cart;
import com.onlineShopping.model.Product;
import com.onlineShopping.model.UserDetails;
import com.onlineShopping.repository.CartRepository;
import com.onlineShopping.repository.ProductRepository;
import com.onlineShopping.repository.UserRepository;
import com.onlineShopping.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * @param productId
     * @param userId
     * @return
     */
    @Override
    public Cart saveCart(Long productId, Long userId) {
        UserDetails userDetails = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();

        Cart cartStatus = cartRepository.findByProductIdAndUserId(productId, userId);
        Cart cart = null;
        if (ObjectUtils.isEmpty(cartStatus)) {
            cart = new Cart();
            cart.setProduct(product);
            cart.setUser(userDetails);
            cart.setQuantity(1);
            cart.setTotalPrice(1.0 * (product.getDiscount()));
        }else {
            cart = cartStatus;
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotalPrice(cart.getQuantity() * cart.getProduct().getDiscountPrice());

        }

        return cartRepository.save(cart);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<Cart> getCartByUser(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        double totalOrderPrice = 0.0;
        List<Cart> updatedCarts = new ArrayList<Cart>();
        for(Cart cart : carts) {
            double totalPrice = (cart.getProduct().getDiscountPrice() * cart.getQuantity());
            cart.setTotalPrice(totalPrice);
            totalOrderPrice += totalPrice;
            cart.setTotalOrderPrice(totalOrderPrice);
            updatedCarts.add(cart);
        }
        return updatedCarts;
    }

    @Override
    public Integer getCountCart(Long userId) {
        Integer countByUserId = cartRepository.countByUserId(userId);
        return countByUserId;
    }

    /**
     * @param sy
     * @param cartId
     */
    @Override
    public void updateQuantity(String sy, Long cartId) {
        Cart cart = cartRepository.findById(cartId).get();
        int updateQuantity;

        if (sy.equalsIgnoreCase("product")) {
            updateQuantity = cart.getQuantity() - 1;
            if (updateQuantity <= 0) {
                cartRepository.delete(cart);
            }else{
                cart.setQuantity(updateQuantity);
                cartRepository.save(cart);
            }
        }else {
            updateQuantity = cart.getQuantity()+1;
            cart.setQuantity(updateQuantity);
            cartRepository.save(cart);
        }
    }
}
