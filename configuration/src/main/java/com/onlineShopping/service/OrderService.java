package com.onlineShopping.service;

import com.onlineShopping.model.OrderRequest;
import com.onlineShopping.model.ProductOrder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    public void saveOrder(Long userId, OrderRequest orderRequest) throws Exception;

    public List<ProductOrder> getOrderByUser(Long userId);

    public ProductOrder updateOrderStatus(Long id, String status);

    public List<ProductOrder> getAllOrders();

    public ProductOrder getOrderByOrderId(String orderId);

    public Page<ProductOrder> getAllOrderPagination(Integer pageNo, Integer pageSize);
}
