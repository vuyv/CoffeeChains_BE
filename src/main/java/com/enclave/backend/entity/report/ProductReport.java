package com.enclave.backend.entity.report;

import lombok.Data;

@Data
public class ProductReport {
    private int id;
    private String name;
    private Integer quantity;
    private double revenue;
}
