package com.onlineShopping.repository;

import com.onlineShopping.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findByUserId(Long userId);

    ProductOrder findByOrderId(String orderId);
}
