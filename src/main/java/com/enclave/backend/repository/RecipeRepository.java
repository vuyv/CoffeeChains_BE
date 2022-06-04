package com.enclave.backend.repository;

import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Product;
import com.enclave.backend.entity.Recipe;
import com.enclave.backend.entity.RecipeId;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, RecipeId> {
    @Query("SELECT r.material.id, r.material.name as Name, r.amount as Amount FROM Recipe r WHERE r.product.id= :productId")
    List<Object[]> getRecipeByProduct(@Param("productId") short productId);
}
