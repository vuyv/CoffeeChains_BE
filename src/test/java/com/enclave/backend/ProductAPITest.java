package com.enclave.backend;

import com.enclave.backend.api.ProductAPI;
import com.enclave.backend.entity.Category;
import com.enclave.backend.entity.Product;
import com.enclave.backend.repository.ProductRepository;
import com.enclave.backend.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductAPI.class)
public class ProductAPITest {

    public static final String CONTEXT_PATH = "/api/";
    public static final String BASE_URI = "http://localhost:8080";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testCreateProduct() throws Exception {
        Category category = new Category();
        category.setId((short) 001);
        category.setName("Tea");
        Product product = new Product();
        product.setId((short) 010);
        product.setName("Green");
        product.setPrice(20);
        product.setImage("");
        product.setStatus(Product.Status.AVAILABLE);

        Mockito.when(productRepository.save(product)).thenReturn(product);
        String json = OBJECT_MAPPER.writeValueAsString(product);
        mockMvc.perform(post("/product/new").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.price", Matchers.equalTo(20)));
    }

}
