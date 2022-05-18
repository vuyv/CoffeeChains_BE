package com.enclave.backend.entity;

import lombok.Data;

@Data
public class RevenueReport {
    private int id;
    private String branch;
    private String address;
    private Integer quantity;
    private double revenue;
}
