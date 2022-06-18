package com.enclave.backend.converter;

import com.enclave.backend.dto.RecipeDTO;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Product;
import com.enclave.backend.entity.Recipe;
import com.enclave.backend.service.MaterialService;
import com.enclave.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeConverter {
    @Autowired
    private ProductService productService;

    @Autowired
    private MaterialService materialService;

    public Recipe toEntity(RecipeDTO dto){
        Recipe entity = new Recipe();
        Product product = productService.getProductById(dto.getProductId());
        entity.setProduct(product);
        Material material = materialService.getMaterialById(dto.getMaterialId());
        entity.setMaterial(material);
        entity.setAmount(dto.getAmount());
        return entity;
    }
}
