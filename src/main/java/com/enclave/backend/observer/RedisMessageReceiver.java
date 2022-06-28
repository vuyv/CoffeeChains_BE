package com.enclave.backend.observer;

import com.enclave.backend.dto.CustomRecipeDTO;
import com.enclave.backend.dto.RecipeResponseDTO;
import com.enclave.backend.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class RedisMessageReceiver implements MessageListener {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RecipeService recipeService;

    @Lazy
    @Autowired
    private StringRedisTemplate redisTemplate;

//    private static Map<Short, List<CustomRecipeDTO>> materialToProductMap;
//
//    @PostConstruct
//    public void postConstruct(){
//        materialToProductMap = recipeService.getProductsByMaterial();
//    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
           Map<Short, List<CustomRecipeDTO>> materialToProductMap = recipeService.getProductsByMaterial();

            List<Short> updatedMaterialIds = objectMapper.readValue(message.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Short.class));

            Map<String, Integer> result = new HashMap<>();

            for (short updatedMaterialId : updatedMaterialIds){
                List <CustomRecipeDTO> updatedProducts = materialToProductMap.get(updatedMaterialId);
                updatedProducts.forEach(product ->{
                    int estimateQuantity = estimateProductInStock(product.getProductId());
                    result.put(product.getProductName(),estimateQuantity);
                    redisTemplate.boundValueOps(product.getProductName()).set(String.valueOf(estimateQuantity));
                });
            }
            System.out.println("RESULT " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        log.info("Message received: " + new String(message.getBody()));
    }

    private int estimateProductInStock(short productId) {
        List<RecipeResponseDTO> recipeByProduct = recipeService.getRecipeByProduct(productId);

        List<Integer> numbersEstimate = new ArrayList<>();
        System.out.println("PRODUCT ID " + productId);
        for (RecipeResponseDTO recipe : recipeByProduct) {

            if (redisTemplate.hasKey(recipe.getMaterialName()) == false) {
                return 0;
            }
            int totalRateOnRedis = Integer.parseInt(redisTemplate.boundValueOps(recipe.getMaterialName()).get());

            if (totalRateOnRedis == 0) return 0;

            int inStock = totalRateOnRedis / recipe.getAmount();

            System.out.println(recipe.getMaterialName() + " ON REDIS: " + totalRateOnRedis);
//            System.out.println("Estimate: " + inStock);

            numbersEstimate.add(inStock);
        }

        int estimate = Collections.min(numbersEstimate);
        System.out.println("=> AVAILABLE : " + estimate);
        return estimate;
    }
}
