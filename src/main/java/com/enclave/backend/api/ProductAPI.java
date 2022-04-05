package com.enclave.backend.api;

import com.enclave.backend.dto.ProductDTO;
import com.enclave.backend.entity.Employee;
import com.enclave.backend.entity.Product;
import com.enclave.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductAPI {

    @Autowired
    private ProductService productService;

    @PostMapping("/new")
    public Product createProduct(@RequestBody ProductDTO dto) {
        return productService.createProduct(dto);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable ("id") short id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @GetMapping("/all")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable ("id") short id) {
        return productService.getProductById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> findByCategory(@PathVariable("categoryId") short categoryid){
        return productService.findByCategory(categoryid);
    }

    @PutMapping("/disable/{id}")
    public Product disableProduct(@PathVariable("id") short id){
        return productService.disableProduct(id);
    }
}
