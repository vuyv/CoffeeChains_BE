package com.enclave.backend.service.impl;

import com.enclave.backend.converter.RecipeConverter;
import com.enclave.backend.dto.RecipeDTO;
import com.enclave.backend.dto.RecipeResponseDTO;
import com.enclave.backend.entity.Inventory;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Product;
import com.enclave.backend.entity.Recipe;
import com.enclave.backend.repository.RecipeRepository;
import com.enclave.backend.service.InventoryService;
import com.enclave.backend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeConverter recipeConverter;
    @Autowired
    private InventoryService inventoryService;
    @Override
    public Recipe createRecipe(RecipeDTO recipeDTO) {
        Recipe newRecipe = recipeConverter.toEntity(recipeDTO);
        return recipeRepository.save(newRecipe);
    }

    @Override
    public List<RecipeResponseDTO> getRecipeByProduct(short productId) {
        List<RecipeResponseDTO> responseDTOS = new ArrayList<>();
        List<Object[]> response = recipeRepository.getRecipeByProduct(productId);
        for(Object[] item : response){
            RecipeResponseDTO dto = new RecipeResponseDTO();
            dto.setMaterialId(Short.parseShort(String.valueOf(item[0])));
            dto.setMaterialName(String.valueOf(item[1]));
            dto.setAmount(Integer.parseInt(String.valueOf(item[2])));
            responseDTOS.add(dto);
        }
        return responseDTOS;
    }

    @Override
    public int estimateProductInStock(short productId) {
        int estimate = 0;
        List<RecipeResponseDTO> recipeByProduct = getRecipeByProduct(productId);
        for (RecipeResponseDTO material: recipeByProduct) {
            Inventory inventory = (Inventory) inventoryService.getMaterialsByBranch();
        }
        return estimate;
    }
}
