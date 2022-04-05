package com.enclave.backend.dto;

import com.enclave.backend.entity.Product;
import lombok.Data;

@Data
public class ProductDTO {

    private short id;
    private String name;
    private short categoryId;
    private double price;
    private String image;
//    Product.Status status;
}
