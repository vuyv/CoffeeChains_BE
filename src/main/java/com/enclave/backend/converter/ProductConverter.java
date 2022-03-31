package com.enclave.backend.converter;

import com.enclave.backend.dto.ProductDTO;
import com.enclave.backend.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public Product toEntity(ProductDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setImage(dto.getImage());
//        entity.setStatus(dto.getStatus());
        return entity;
    }
}
