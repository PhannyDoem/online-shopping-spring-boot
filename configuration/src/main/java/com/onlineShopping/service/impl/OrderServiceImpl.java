package com.onlineShopping.service.impl;

import com.onlineShopping.model.Cart;
import com.onlineShopping.model.OrderAddress;
import com.onlineShopping.model.OrderRequest;
import com.onlineShopping.model.ProductOrder;
import com.onlineShopping.repository.CartRepository;
import com.onlineShopping.repository.ProductOrderRepository;
import com.onlineShopping.service.OrderService;
import com.onlineShopping.util.CommonUtil;
import com.onlineShopping.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CommonUtil commonUtil;


    /**
     * @param userId
     * @param orderRequest
     * @throws Exception
     */
    @Override
    public void saveOrder(Long userId, OrderRequest orderRequest) throws Exception {

        List<Cart> carts = cartRepository.findByUserId(userId);
        for (Cart cart : carts) {
            ProductOrder productOrder = new ProductOrder();

            productOrder.setOrderId(UUID.randomUUID().toString());
            productOrder.setOrderDate(LocalDate.now());

            productOrder.setProduct(cart.getProduct());
            productOrder.setPrice(cart.getProduct().getPrice());

            productOrder.setProduct(cart.getProduct());
            productOrder.setPrice(cart.getProduct().getDiscountPrice());

            productOrder.setQuantity(cart.getQuantity());
            productOrder.setUser(cart.getUser());

            productOrder.setStatus(OrderStatus.IN_PROGRESS.getName());
            productOrder.setPaymentType(orderRequest.getPaymentType());

            OrderAddress address = new OrderAddress();
            address.setFirstName(orderRequest.getFirstName());
            address.setLastName(orderRequest.getLastName());
            address.setEmail(orderRequest.getEmail());
            address.setMobileNo(orderRequest.getMobileNo());
            address.setAddress(orderRequest.getAddress());
            address.setCity(orderRequest.getCity());
            address.setState(orderRequest.getState());
            address.setPinCode(orderRequest.getPinCode());

            productOrder.setOrderAddress(address);

            ProductOrder saveOrder = productOrderRepository.save(productOrder);
            commonUtil.sendMailForProductOrder(saveOrder, "success");
        }
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<ProductOrder> getOrderByUser(Long userId) {
        return productOrderRepository.findByUserId(userId);
    }

    /**
     * @param id
     * @param status
     * @return
     */
    @Override
    public ProductOrder updateOrderStatus(Long id, String status) {
        Optional<ProductOrder> findById = productOrderRepository.findById(id);
        if (findById.isPresent()) {
            ProductOrder productOrder = findById.get();
            productOrder.setStatus(status);
            return productOrderRepository.save(productOrder);
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<ProductOrder> getAllOrders() {
        return productOrderRepository.findAll();
    }

    /**
     * @param orderId
     * @return
     */
    @Override
    public ProductOrder getOrderByOrderId(String orderId) {
        return productOrderRepository.findByOrderId(orderId);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<ProductOrder> getAllOrderPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return productOrderRepository.findAll(pageable);
    }
}
