package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orderDetail")
public class OrderDetail{

    @EmbeddedId
    private OrderDetailId orderDetailId = new OrderDetailId();

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("orderId")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId("productId")
    private Product product;

    @Column
    private short quantity;
}
