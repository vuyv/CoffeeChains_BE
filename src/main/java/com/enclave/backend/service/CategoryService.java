package com.enclave.backend.service;

import com.enclave.backend.dto.CategoryDTO;
import com.enclave.backend.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryDTO categoryDTO);

    Category updateCategory(Category category);

    List<Category> getCategories();

    Category getCategoryById(short id);
}
