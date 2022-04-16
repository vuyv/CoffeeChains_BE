package com.enclave.backend.service;

import com.enclave.backend.dto.OrderDTO;
import com.enclave.backend.entity.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    ResponseEntity<Order> createNewOrder(OrderDTO orderDTO);

    Order findOrderById(String id);

    Optional<Order> findOrderByOrdinalNumber(int ordinalNumber);

    Order cancelOrder(String id);

    boolean isValidTotal(OrderDTO orderDTO, double total);

    List<Order> getOrders();

    List<Order> getOrdersInBranch();

    Order findOrderByIdInBranch(short branchId, String orderId);

    List<Order> findOrdersInCurrentDayInBranch();

    List<Order> findOrdersInAWeekInBranch();

    List<Order> findOrdersInAMonthInBranch();
}
