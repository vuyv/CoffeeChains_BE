package com.enclave.backend.service.impl;

import com.enclave.backend.converter.RecipeConverter;
import com.enclave.backend.dto.RecipeDTO;
import com.enclave.backend.dto.RecipeResponseDTO;
import com.enclave.backend.entity.*;
import com.enclave.backend.repository.RecipeRepository;
import com.enclave.backend.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeConverter recipeConverter;

    @Autowired
    private final StringRedisTemplate redisTemplate;

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
        List<RecipeResponseDTO> recipeByProduct = getRecipeByProduct(productId);
        List<Integer> numbersEstimate = new ArrayList<>();

            for (RecipeResponseDTO recipe: recipeByProduct) {
                if(redisTemplate.hasKey(recipe.getMaterialName()) == false){
                    return 0;
                }
                    int totalRateOnRedis = Integer.parseInt(redisTemplate.boundValueOps(recipe.getMaterialName()).get());
                    int inStock = totalRateOnRedis / recipe.getAmount();
                    System.out.println(recipe.getMaterialName() + " ON REDIS: " + totalRateOnRedis);
                    System.out.println("Estimate: " +  + inStock);
                    numbersEstimate.add(inStock);
//                }
            }

//        if(numbersEstimate.size() != recipeByProduct.size()){
//            numbersEstimate.add(0);
//        }

        int estimate = Collections.min(numbersEstimate);
        System.out.println("=> AVAILABLE : " + estimate);
        return estimate;
    }

}
