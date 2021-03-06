package com.enclave.backend.service;

import com.enclave.backend.dto.OrderDTO;
import com.enclave.backend.entity.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    ResponseEntity<Order> createNewOrder(OrderDTO orderDTO);

    Order findOrderById(String id);

    Order findOrderByOrdinalNumber(int ordinalNumber);

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
    int getCountOfEachBranchOrderByDate(String date, short branchId);

    double getCountOfBranchTotalPriceByDate(String date);
    double getCountOfEachBranchTotalPriceByDate(String date, short branchId);

    List<Object[]> getCountOfTotalPriceInBranchWeekly(String date);
    List<Object[]> getCountOfTotalPriceEachBranchWeekly(String date, short branchId);

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
    double getWeeklyRevenueInBranch(short branchId);

    double getCurrentMonthRevenueEachBranch();
    double getCurrentMonthRevenueInBranch(short branchId);

    double compareLastMonthBranchRevenue();
    double compareLastMonthOfBranchRevenue(short branchId);

    List<Object[]> getOrderQuantityByStatus();
    List<Object[]> getOrderQuantityByStatusEachBranch(short branchId);

    List<Object[]> getTopProducts(String date);
    List<Object[]> getTopProductsEachBranch(String date, short branchId);
}
