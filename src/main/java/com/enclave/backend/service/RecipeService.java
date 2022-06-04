package com.enclave.backend.service;

import com.enclave.backend.dto.RecipeDTO;
import com.enclave.backend.dto.RecipeResponseDTO;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Product;
import com.enclave.backend.entity.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe(RecipeDTO recipeDTO);
    List<RecipeResponseDTO> getRecipeByProduct(short productId);
    int estimateProductInStock(short productId);
}
