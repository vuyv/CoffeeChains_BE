package com.enclave.backend.converter;

import com.enclave.backend.dto.OrderDetailDTO;
import com.enclave.backend.entity.OrderDetail;
import com.enclave.backend.entity.Product;
import com.enclave.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailConverter {
    @Autowired
    private ProductService productService;

    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        OrderDetail entity = new OrderDetail();
        entity.setQuantity(orderDetailDTO.getQuantity());
        Product product = productService.getProductById(orderDetailDTO.getProduct().getId());
        entity.setProduct(product);
        return entity;
    }
}
