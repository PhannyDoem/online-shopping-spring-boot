package com.onlineShopping.repository;

import com.onlineShopping.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public Cart findByProductIdAndUserId(Long product_id, Long user_id);

    public Integer countByUserId(Long userId);

    public List<Cart> findByUserId(Long userId);
}
