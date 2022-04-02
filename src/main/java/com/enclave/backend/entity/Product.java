package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private short id;

    @Column(length = 30)
    private String name;

    @ManyToOne
//    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String image;

    @Column
    private double price;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
        AVAILABLE, UNAVAILABLE
    }
}
