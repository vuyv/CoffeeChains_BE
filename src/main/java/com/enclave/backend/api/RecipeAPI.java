package com.enclave.backend.api;

import com.enclave.backend.dto.RecipeDTO;
import com.enclave.backend.dto.RecipeResponseDTO;
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
    public List<Recipe> createRecipe(@RequestBody List <RecipeDTO> dtos) {
        return recipeService.createRecipe(dtos);
    }

    @GetMapping("/byProduct/{id}")
    public List<RecipeResponseDTO> getRecipeByProduct(@PathVariable ("id") short id){
        return recipeService.getRecipeByProduct(id);
    }

    @GetMapping("/estimate/product/inStock/{id}")
    public int estimateProductInStock(@PathVariable ("id") short id){
        return recipeService.estimateProductInStock(id);
    }

    @PutMapping("/update")
    public List<Recipe> updateRecipe(@RequestBody List<RecipeDTO> recipeDTOs){
        return recipeService.updateRecipe(recipeDTOs);
    }

    @GetMapping("/{id}")
    public List<Recipe> getRecipeByProductId(@PathVariable ("id")short productId) {
        return recipeService.getRecipeByProductId(productId);
    }
}
