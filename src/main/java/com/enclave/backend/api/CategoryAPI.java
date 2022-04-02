package com.enclave.backend.api;

import com.enclave.backend.dto.CategoryDTO;
import com.enclave.backend.entity.Category;
import com.enclave.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryAPI {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping("/new")
    public Category createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @PutMapping("/{id}")
    public Category editCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable ("id") short id){
        return categoryService.getCategoryById(id);
    }
}
