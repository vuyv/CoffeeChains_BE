package com.enclave.backend.repository;

import com.enclave.backend.entity.Recipe;
import com.enclave.backend.entity.RecipeId;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, RecipeId> {
    @Query("SELECT r.material.id, r.material.name as Name, r.amount as Amount, r.material.image FROM Recipe r WHERE r.product.id= :productId")
    List<Object[]> getRecipeByProduct(@Param("productId") short productId);

    @Query("SELECT r FROM Recipe r where r.material.id = :materialId and r.product.id= :productId")
    Recipe getRecipeByRecipeId(@Param("productId") short productId, @Param("materialId") short materialId);

    @Query("SELECT r.product.id, r.product.name FROM Recipe r where r.material.id = :materialId")
    List<Object[]> getRecipesByMaterialId(@Param("materialId") short materialId);
}
