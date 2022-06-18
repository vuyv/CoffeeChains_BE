package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product{

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
