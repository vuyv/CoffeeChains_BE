package com.enclave.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(length = 15)
    private String id;

    @ManyToOne
    @JoinColumn(name = "discount_code")
    private Discount discount;

    @Column
    private double totalPrice;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Employee createdBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "canceledBy")
    private Employee canceledBy;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public enum Status{
        CREATED, CANCELED
    }
}
