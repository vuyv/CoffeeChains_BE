package com.enclave.backend.repository;

import com.enclave.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Short> {
    Optional<Product> findById(Short id);
    List<Product> findByIdIn(List<Short> ids);
}
