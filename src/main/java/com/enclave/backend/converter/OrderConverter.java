package com.enclave.backend.converter;

import com.enclave.backend.dto.OrderDTO;
import com.enclave.backend.entity.Order;
import com.enclave.backend.service.OrderIdGenerator;
import com.enclave.backend.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderConverter {

    @Autowired
    private OrderDetailConverter orderDetailConverter;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private OrderIdGenerator orderIdGenerator;

    public Order toEntity(OrderDTO orderDTO) {
        Order entity = new Order();
////        entity.setTotalPrice(orderDTO.getTotalPrice());
//        entity.setCanceledBy(null);
//        entity.setStatus(Order.Status.CREATED);
//        entity.setCreatedBy(employeeService.getCurrentEmployee());
        return entity;
    }
}
