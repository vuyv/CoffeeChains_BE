package com.enclave.backend.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private short quantity;
    private ProductInOrderDTO product;
}
