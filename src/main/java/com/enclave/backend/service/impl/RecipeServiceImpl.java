package com.enclave.backend.service.impl;

import com.enclave.backend.converter.RecipeConverter;
import com.enclave.backend.dto.CustomRecipeDTO;
import com.enclave.backend.dto.RecipeDTO;
import com.enclave.backend.dto.RecipeResponseDTO;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Recipe;
import com.enclave.backend.repository.RecipeRepository;
import com.enclave.backend.service.MaterialService;
import com.enclave.backend.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.*;

//@AllArgsConstructor
@Service
//@Slf4j
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeConverter recipeConverter;

    @Autowired
    private MaterialService materialService;

    @Override
    public List<Recipe> createRecipe(List<RecipeDTO> recipeDTOs) {
        List<Recipe> recipes = new ArrayList<>();
        recipeDTOs.forEach(recipeDTO -> {
            Recipe newRecipe = recipeConverter.toEntity(recipeDTO);
            recipes.add(newRecipe);
        });
        return recipeRepository.saveAll(recipes);
    }

    @Override
    public List<RecipeResponseDTO> getRecipeByProduct(short productId) {
        List<RecipeResponseDTO> responseDTOS = new ArrayList<>();
        List<Object[]> response = recipeRepository.getRecipeByProduct(productId);
        for (Object[] item : response) {
            RecipeResponseDTO dto = new RecipeResponseDTO();
            dto.setMaterialId(Short.parseShort(String.valueOf(item[0])));
            dto.setMaterialName(String.valueOf(item[1]));
            dto.setAmount(Integer.parseInt(String.valueOf(item[2])));
            dto.setImage(String.valueOf(item[3]));
            responseDTOS.add(dto);
        }
        return responseDTOS;
    }

    @Override
    public Recipe getRecipeByRecipeId(short productId, short materialId) {
        return recipeRepository.getRecipeByRecipeId(productId, materialId);
    }

    @Override
    public List<CustomRecipeDTO> getRecipesByMaterialId(short materialId) {
        List<Object[]> list = recipeRepository.getRecipesByMaterialId(materialId);
        List<CustomRecipeDTO> recipes = new ArrayList<>();
        for (Object[] obj : list) {
            CustomRecipeDTO dto = new CustomRecipeDTO();
            dto.setProductId((Short) obj[0]);
            dto.setProductName(String.valueOf(obj[1]));
            recipes.add(dto);
        }
        return recipes;
    }

    @Override
    public Map<Short, List<CustomRecipeDTO>> getProductsByMaterial() {
        Map<Short, List<CustomRecipeDTO>> map = new HashMap<>();

        List<Material> materials = materialService.getAllMaterial();
        for (Material material : materials) {
            List<CustomRecipeDTO> products = getRecipesByMaterialId(material.getId());
            map.put(material.getId(), products);
            System.out.println("MAPPPP " + map);
        }
        return map;
    }

    @Override
    public List<Recipe> updateRecipe(List<RecipeDTO> recipeDTOs) {
        for (RecipeDTO recipeDTO : recipeDTOs) {
            Recipe recipe = getRecipeByRecipeId(recipeDTO.getProductId(), recipeDTO.getMaterialId());
            if (Objects.nonNull(recipe)) {
                recipe.setAmount(recipeDTO.getAmount());
                recipeRepository.save(recipe);
            }
//            else {
//                Recipe newRecipe = recipeConverter.toEntity(recipeDTO);
//                recipeRepository.save(newRecipe);
//            }
        }
        return null;
    }
}
