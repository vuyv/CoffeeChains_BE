package com.enclave.backend.service.impl;

import com.enclave.backend.converter.RecipeConverter;
import com.enclave.backend.dto.CustomRecipeDTO;
import com.enclave.backend.dto.ProductResponseDTO;
import com.enclave.backend.dto.RecipeDTO;
import com.enclave.backend.dto.RecipeResponseDTO;
import com.enclave.backend.entity.Recipe;
import com.enclave.backend.repository.RecipeRepository;
import com.enclave.backend.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Configuration
@AllArgsConstructor
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    public RecipeServiceImpl() {
        log.info("init RecipeServiceImpl");
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeConverter recipeConverter;

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
            responseDTOS.add(dto);
        }
        return responseDTOS;
    }

    @Override
    public int estimateProductInStock(short productId) {
        List<RecipeResponseDTO> recipeByProduct = getRecipeByProduct(productId);
        List<Integer> numbersEstimate = new ArrayList<>();

        for (RecipeResponseDTO recipe : recipeByProduct) {
            if (redisTemplate.hasKey(recipe.getMaterialName()) == false) {
                return 0;
            }
            int totalRateOnRedis = Integer.parseInt(redisTemplate.boundValueOps(recipe.getMaterialName()).get());
            int inStock = totalRateOnRedis / recipe.getAmount();
            System.out.println(recipe.getMaterialName() + " ON REDIS: " + totalRateOnRedis);
            System.out.println("Estimate: " + inStock);
            numbersEstimate.add(inStock);
        }

        int estimate = Collections.min(numbersEstimate);
        System.out.println("=> AVAILABLE : " + estimate);
        return estimate;
    }

    @Override
    public List<ProductResponseDTO> estimateByMaterial(short materialId) {
        List<CustomRecipeDTO> listRecipes = getRecipesByMaterialId(materialId);
        List<ProductResponseDTO> list = new ArrayList<>();
        for (CustomRecipeDTO dto: listRecipes) {
            short id = dto.getProductId();
            int num = estimateProductInStock(id);
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setProductId(id);
            productResponseDTO.setQuantity(num);
            list.add(productResponseDTO);
        }
        System.out.println("ESTIMATE MATERIAL " + materialId + list);
        return list;
    }

    @Override
    public Recipe getRecipeByRecipeId(short productId, short materialId) {
        return recipeRepository.getRecipeByRecipeId(productId, materialId);
    }

    @Override
    public List<CustomRecipeDTO> getRecipesByMaterialId(short materialId) {
        List <Object[]> list = recipeRepository.getRecipesByMaterialId(materialId);
        List <CustomRecipeDTO> recipes = new ArrayList<>();
        for (Object[] obj : list){
            CustomRecipeDTO dto = new CustomRecipeDTO();
            dto.setProductId((Short) obj[0]);
            dto.setProductName(String.valueOf(obj[1]));
            recipes.add(dto);
        }
        return recipes;
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
