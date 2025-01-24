package com.onlineShopping.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private UserDetails user;

    @ManyToOne
    private Product product;

    private Integer quantity;

    @Transient
    private Double totalPrice;

    @Transient
    private Double totalOrderPrice;

    public Cart() {}

    public Cart(Integer id, Product product, Integer quantity, Double totalOrderPrice, Double totalPrice, UserDetails user) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.totalOrderPrice = totalOrderPrice;
        this.totalPrice = totalPrice;
        this.user = user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTotalOrderPrice(Double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }
}
