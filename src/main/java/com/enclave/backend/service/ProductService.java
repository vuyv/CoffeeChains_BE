package com.enclave.backend.service;

import com.enclave.backend.dto.ProductDTO;
import com.enclave.backend.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductDTO productDTO);

    Product updateProduct(Product product);

    List<Product> getProducts();

    Product getProductById(short id);

    List<Product> findByCategory(short categoryId);
}
