package com.enclave.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;

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
}
