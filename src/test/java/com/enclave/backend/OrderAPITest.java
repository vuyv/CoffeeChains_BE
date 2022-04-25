package com.enclave.backend;

import com.enclave.backend.api.OrderAPI;
import com.enclave.backend.entity.Employee;
import com.enclave.backend.entity.Order;
import com.enclave.backend.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = OrderAPI.class)
public class OrderAPITest {

    public static final String CONTEXT_PATH = "/api/";
    public static final String BASE_URI = "http://localhost:8088";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @Before
    public void setup(){

    }

    @Test
    public void testListOrders() throws Exception {
        Order order = new Order();
        order.setId("002_220412_009");
        order.setCreatedAt(new Date());
        order.setStatus(Order.Status.CREATED);
        order.setTotalPrice(100);
        order.setCreatedBy(new Employee());
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        mockMvc.perform(get( "/order/all")).andExpect(status().isOk());
    }

    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order();
        order.setId("002_220412_009");
        order.setCreatedAt(new Date());
        order.setStatus(Order.Status.CREATED);
        order.setTotalPrice(100);

        Mockito.when(orderRepository.save(order)).thenReturn(order);
        String json = OBJECT_MAPPER.writeValueAsString(order);
        mockMvc.perform(post(BASE_URI + CONTEXT_PATH + "/order/new").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo("002_220412_009")))
                .andExpect(jsonPath("$.totalPrice", Matchers.equalTo(100)));
    }

}
