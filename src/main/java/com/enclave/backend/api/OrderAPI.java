package com.enclave.backend.api;

import com.enclave.backend.dto.OrderDTO;
import com.enclave.backend.entity.Order;
import com.enclave.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderAPI {

    @Autowired
    private OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<Order> createNewOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createNewOrder(orderDTO);
    }

    @GetMapping("/all")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public Order findOrderById(@PathVariable("id") String id){
        return orderService.findOrderById(id);
    }

    @PutMapping("/cancel/{id}")
    public Order cancelOrder(@PathVariable("id") String id){
        return orderService.cancelOrder(id);
    }

    @GetMapping("/branch/all")
    public List getOrderByBranch() {
        return orderService.getOrdersInBranch();
    }

    @GetMapping("/{branchId}/{orderId}")
    public Order findOrderByIdInBranch( @PathVariable("branchId")short branchId, @PathVariable("orderId") String orderId){
        return orderService.findOrderByIdInBranch(branchId, orderId);
    }

    @GetMapping("/find/{ordinalNumber}")
    public Optional<Order> findOrderByOrdinalNumber(@PathVariable("ordinalNumber") int ordinalNumber){
        return orderService.findOrderByOrdinalNumber(ordinalNumber);
    }

    @GetMapping("/branch/find/day")
    List<Order> findOrdersInCurrentDayInBranch(){
        return orderService.findOrdersInCurrentDayInBranch();
    }

    @GetMapping("branch/find/week")
    List<Order> findOrdersInAWeekInBranch(){
        return orderService.findOrdersInAWeekInBranch();
    }

    @GetMapping("branch/find/month")
    List<Order> findOrdersInAMonthInBranch(){
        return orderService.findOrdersInAMonthInBranch();
    }
}
