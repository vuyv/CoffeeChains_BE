package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "unit_converter")
public class UnitConverter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private short id;

    @Column
    private String unit;

    @Column
    private double netWeight;

    @Column
    private double rate;

}
