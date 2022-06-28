package com.enclave.backend.api;

import com.enclave.backend.entity.Product;
import com.enclave.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PubSubAPI {

   @Autowired
   private RedisTemplate redisTemplate;

   @Autowired
   private ProductService productService;

    @GetMapping("/estimate")
    public Map<String, Integer> estimateProducts(){
        Map<String, Integer> result = new HashMap<>();
        List<Product> productList = productService.getProducts();
        productList.forEach(product -> {
            if(redisTemplate.hasKey(product.getName())){
                result.put(product.getName(), (Integer) redisTemplate.boundValueOps(product.getName()).get());
            } else {
                result.put(product.getName(), 0);
            }
        });
        return result;
    }
}
