package com.enclave.backend.converter;

import com.enclave.backend.dto.CategoryDTO;
import com.enclave.backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }
}
