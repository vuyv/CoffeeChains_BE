package com.enclave.backend.service.impl;

import com.enclave.backend.converter.ProductConverter;
import com.enclave.backend.dto.ProductDTO;
import com.enclave.backend.entity.Category;
import com.enclave.backend.entity.Employee;
import com.enclave.backend.entity.Product;
import com.enclave.backend.repository.CategoryRepository;
import com.enclave.backend.repository.ProductRepository;
import com.enclave.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductConverter productConverter;

    @Override
    public Product createProduct(ProductDTO product) {
        short categoryId = product.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + categoryId));

        Product newProduct = productConverter.toEntity(product);
        newProduct.setCategory(category);
        newProduct.setStatus(Product.Status.AVAILABLE);

        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(short id, ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + productDTO.getCategoryId()));

        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        oldProduct.setName(productDTO.getName());
        oldProduct.setPrice(productDTO.getPrice());
        oldProduct.setImage(productDTO.getImage());
        oldProduct.setCategory(category);
//        oldProduct.setStatus(productDTO.getStatus());

        return productRepository.save(oldProduct);
    }


    //TODO: Pagination
    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(short id) {
        return productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid product id: " + id));
    }

    @Override
    public List<Product> findByCategory(short categoryId) {
        return  productRepository.findByCategory(categoryId);
    }

    @Override
    public Product disableProduct(short id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        product.setStatus(Product.Status.UNAVAILABLE);
        return productRepository.save(product);
    }

}
