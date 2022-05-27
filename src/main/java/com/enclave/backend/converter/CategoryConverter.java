package com.enclave.backend.converter;

import com.enclave.backend.dto.CategoryDTO;
import com.enclave.backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setImage(dto.getImage());
        return category;
    }
}
