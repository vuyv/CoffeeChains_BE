package com.enclave.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orderDetail")
public class OrderDetail{

    @EmbeddedId
    private OrderDetailId orderDetailId = new OrderDetailId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumns({
            @JoinColumn(name = "orderId", referencedColumnName = "id")
    })
    @JsonIgnore
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    @MapsId("productId")
    private Product product;

    @Column
    private short quantity;

//    @Column
//    private double subTotal;
}
