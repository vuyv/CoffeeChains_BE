package com.enclave.backend.service.impl;

import com.enclave.backend.converter.CategoryConverter;
import com.enclave.backend.dto.CategoryDTO;
import com.enclave.backend.entity.Category;
import com.enclave.backend.repository.CategoryRepository;
import com.enclave.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = categoryConverter.toEntity(categoryDTO);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        short categoryId = category.getId();
        Category oldCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + categoryId));
        oldCategory.setName(category.getName());
        return categoryRepository.save(oldCategory);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(short id) {
        return categoryRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid category id " + id));
    }
}
