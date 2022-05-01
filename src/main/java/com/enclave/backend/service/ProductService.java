package com.enclave.backend.service;

import com.enclave.backend.dto.ProductDTO;
import com.enclave.backend.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductDTO productDTO);

    Product updateProduct(short id, ProductDTO productDTO);

    List<Product> getProducts();

    Product getProductById(short id);

    List<Product> findByCategory(short categoryId);

    Product disableProduct(short id);

    Long countAllProduct();

    List<Product> findByCategoryAndStatus(short categoryId, String status);

    List<Product> findByStatus(String status);

}
