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

    //manager
    int getCountOfBranchOrderByDate(String date);

    double getCountOfBranchTotalPriceByDate(String date);

    List<Object[]> getCountOfTotalPriceInBranchWeekly();

    //owner
    double getCountOfAllTotalPriceByDate(String date);

    Integer getCountOfAllOrderByDate(String date);

    List<Object[]> getCountOfOrderEachBranch(String date);

    List<Object[]> getCountOfTotalPriceEachBranch(String date);

    double compareLastMonthRevenue();

    double compareLastWeekRevenue();

    List<Object[]> topWeeklySeller();

    double getCurrentMonthRevenue();

    double getCurrentWeekRevenue();

    List<Object[]> getBestSellingProducts();

    //Report
    List<Object[]> getDailyRevenueAllBranch();

}
