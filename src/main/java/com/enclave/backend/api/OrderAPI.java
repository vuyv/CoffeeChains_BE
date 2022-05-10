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
    public Order findOrderById(@PathVariable("id") String id) {
        return orderService.findOrderById(id);
    }

    @PutMapping("/cancel/{id}")
    public Order cancelOrder(@PathVariable("id") String id) {
        return orderService.cancelOrder(id);
    }

    @GetMapping("/branch/all")
    public List getOrderByBranch() {
        return orderService.getOrdersInBranch();
    }

    @GetMapping("/{branchId}/{orderId}")
    public Order findOrderByIdInBranch(@PathVariable("branchId") short branchId, @PathVariable("orderId") String orderId) {
        return orderService.findOrderByIdInBranch(branchId, orderId);
    }

    @GetMapping("/find/{ordinalNumber}")
    public Optional<Order> findOrderByOrdinalNumber(@PathVariable("ordinalNumber") int ordinalNumber) {
        return orderService.findOrderByOrdinalNumber(ordinalNumber);
    }


    /////////////////////////////////////
    @GetMapping("/branch/daily/{date}")
    List<Order> getDailyOrdersInBranch(@PathVariable("date") String date) {
        return orderService.getDailyOrdersInBranch(date);
    }
    @GetMapping("/branch/weekly/{date}")
    List<Order> getWeeklyOrdersInBranch(@PathVariable("date") String date) {
        return orderService.getWeeklyOrdersInBranch(date);
    }
    @GetMapping("/branch/monthly/{date}")
    List<Order> getMonthlyOrdersInBranch(@PathVariable("date") String date) {
        return orderService.getMonthlyOrdersInBranch(date);
    }


    //manager
    @GetMapping("/branch/count/orderByday/{date}")
    public Integer getCountOfBranchOrderByDate(@PathVariable("date") String date) {
        return orderService.getCountOfBranchOrderByDate(date);
    }

    @GetMapping("/branch/count/totalPriceByday/{date}")
    double getCountOfBranchTotalPriceByDate(@PathVariable("date") String date) {
        return orderService.getCountOfBranchTotalPriceByDate(date);
    }

    @GetMapping("/branch/count/lastweek/{date}")
    public List<Object[]> getCountOfTotalPriceInBranchWeekly(@PathVariable("date") String date) {
        return orderService.getCountOfTotalPriceInBranchWeekly(date);
    }

    @GetMapping("/manager/bestSellingProducts")
    public List<Object[]> getBestSellingProducts() {
        return orderService.getBestSellingProducts();
    }

    //owner
    @GetMapping("/owner/count/orderByday/{date}")
    public Integer getCountOfAllOrderByDate(@PathVariable("date") String date) {
        return orderService.getCountOfAllOrderByDate(date);
    }

    @GetMapping("/owner/count/totalPriceByday/{date}")
    public double getCountOfAllTotalPriceByDate(@PathVariable("date") String date) {
        return orderService.getCountOfAllTotalPriceByDate(date);
    }

    @GetMapping("/owner/count/ordersByday/eachBranch/{date}")
    public List<Object[]> getCountOfOrderEachBranch(@PathVariable("date") String date) {
        return orderService.getCountOfOrderEachBranch(date);
    }

    @GetMapping("/owner/count/totalPriceByday/eachBranch/{date}")
    public List<Object[]> getCountOfTotalPriceEachBranch(@PathVariable("date") String date) {
        return orderService.getCountOfTotalPriceEachBranch(date);
    }

    @GetMapping("/owner/compare/lastMonth")
    public double compareLastMonthRevenue() {
        return orderService.compareLastMonthRevenue();
    }

    @GetMapping("/owner/compare/lastWeek")
    public double compareLastWeekRevenue() {
        return orderService.compareLastWeekRevenue();
    }

    @GetMapping("/owner/topSeller/weekly")
    public List<Object[]> topWeeklySeller() {
        return orderService.topWeeklySeller();
    }

    @GetMapping("/owner/revenue/currentMonth")
    public double getCurrentMonthRevenue() {
        return orderService.getCurrentMonthRevenue();
    }

    @GetMapping("/owner/revenue/currentWeek")
    public double getCurrentWeekRevenue() {
        return orderService.getCurrentWeekRevenue();
    }

    @GetMapping("/manager/revenue/currentWeek")
    public double getWeeklyRevenueEachBranch() {
        return orderService.getWeeklyRevenueEachBranch();
    }

    @GetMapping("/manager/revenue/currentMonth")
    public double getCurrentMonthRevenueEachBranch() {
        return orderService.getCurrentMonthRevenueEachBranch();
    }

    @GetMapping("/manager/compare/lastMonth")
    public double compareLastMonthBranchRevenue(){
        return orderService.compareLastMonthBranchRevenue();
    }

    @GetMapping("/manager/orderQuantity/monthly")
    public List<Object[]> getOrderQuantityByStatus(){
        return orderService.getOrderQuantityByStatus();
    }

    @GetMapping("/manager/topProducts/last3Months/{date}")
    public List<Object[]> getTopProductsLast3Months(@PathVariable("date") String date){
        return orderService.getTopProducts(date);
    }
}