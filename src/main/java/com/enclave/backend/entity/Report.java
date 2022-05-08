package com.enclave.backend.entity;

import lombok.Data;

@Data
public class Report {
    private int id;
    private String name;
    private Integer quantity;
    private double revenue;
}
