package com.enclave.backend.api;

import com.enclave.backend.dto.ProductDTO;
import com.enclave.backend.dto.RecipeDTO;
import com.enclave.backend.dto.RecipeResponseDTO;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Product;
import com.enclave.backend.entity.Recipe;
import com.enclave.backend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeAPI {
    @Autowired
    private RecipeService recipeService;

    @PostMapping("/new")
    public Recipe createRecipe(@RequestBody RecipeDTO dto) {
        return recipeService.createRecipe(dto);
    }

    @GetMapping("/byProduct/{id}")
    public List<RecipeResponseDTO> getRecipeByProduct(@PathVariable ("id") short id){
        return recipeService.getRecipeByProduct(id);
    }

    @GetMapping("/estimate/product/inStock/{id}")
    public int estimateProductInStock(@PathVariable ("id") short id){
        return recipeService.estimateProductInStock(id);
    }
}
