package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class OrderDetailId implements Serializable {

    @Column(name = "orderId", length = 15)
    private String orderId;

    @Column(name = "productId")
    private short productId;
}
