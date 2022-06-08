package com.enclave.backend.service;

import com.enclave.backend.dto.RecipeDTO;
import com.enclave.backend.dto.RecipeResponseDTO;
import com.enclave.backend.entity.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> createRecipe(List<RecipeDTO> recipeDTO);

    List<RecipeResponseDTO> getRecipeByProduct(short productId);

    int estimateProductInStock(short productId);

    Recipe getRecipeByRecipeId(short productId, short materialId);

    List<Recipe> updateRecipe(List<RecipeDTO> recipeDTO);

    List<Recipe> getRecipeByProductId(short productId);
}
