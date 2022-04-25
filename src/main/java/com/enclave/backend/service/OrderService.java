package com.enclave.backend.service;

import com.enclave.backend.dto.OrderDTO;
import com.enclave.backend.entity.Branch;
import com.enclave.backend.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    int getCountOfBranchOrderByDate (String date);

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

    //REPORT
    //revenue
    List<Object[]> getDailyRevenueAllBranch();

    List<Object[]> getWeeklyRevenueAllBranch();

    List<Object[]> getMonthlyRevenueAllBranch();

    //product
    List<Object[]> getDailyProductAllCategory();

    List<Object[]> getWeeklyProductAllCategory();

    List<Object[]> getMonthlyProductAllCategory();

    List<Object[]> getDailyProductByCategory(short categoryId);

    List<Object[]> getWeeklyProductByCategory(short categoryId);

    List<Object[]> getMonthlyProductByCategory(short categoryId);



    List<Object[]> getCustomDailyRevenueAllBranch(String date);

    List<Object[]> getCustomWeeklyRevenueAllBranch(String date);

    List<Object[]> getCustomMonthlyRevenueAllBranch(String date);

}
