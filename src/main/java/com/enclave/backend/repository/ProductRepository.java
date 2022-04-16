package com.enclave.backend.repository;

import com.enclave.backend.entity.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Short> {
    Optional<Product> findById(Short id);
    List<Product> findByIdIn(List<Short> ids);

    @Query("SELECT p FROM Product p WHERE p.category.id= :categoryId")
    List<Product> findByCategory(@Param("category") short categoryId);

}
