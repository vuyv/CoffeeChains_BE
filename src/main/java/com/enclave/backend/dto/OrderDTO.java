package com.enclave.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private String discount_code;
    private double totalPrice;
    private List<OrderDetailDTO> orderDetails;
}
