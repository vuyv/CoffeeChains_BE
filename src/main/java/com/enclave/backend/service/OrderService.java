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

    /////////////////
    List<Order> getDailyOrdersInBranch(String date);

    List<Order> getWeeklyOrdersInBranch(String date);

    List<Order> getMonthlyOrdersInBranch(String date);

    //manager
    int getCountOfBranchOrderByDate(String date);

    double getCountOfBranchTotalPriceByDate(String date);

    List<Object[]> getCountOfTotalPriceInBranchWeekly(String date);

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
    // MANAGER
    double getWeeklyRevenueEachBranch();

    double getCurrentMonthRevenueEachBranch();

    double compareLastMonthBranchRevenue();

    List<Object[]> getOrderQuantityByStatus();

    List<Object[]> getTopProducts(String date);

}
