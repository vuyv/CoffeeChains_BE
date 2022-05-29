package com.enclave.backend.api;

import com.enclave.backend.dto.OrderDTO;
import com.enclave.backend.entity.Order;
import com.enclave.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Order findOrderByOrdinalNumber(@PathVariable("ordinalNumber") int ordinalNumber) {
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
    @GetMapping("/branch/{branchId}/count/orderByday/{date}")
    public Integer getCountOfEachBranchOrderByDate(@PathVariable("date") String date, @PathVariable("branchId") short branchId) {
        return orderService.getCountOfEachBranchOrderByDate(date, branchId);
    }

    @GetMapping("/branch/count/totalPriceByday/{date}")
    double getCountOfBranchTotalPriceByDate(@PathVariable("date") String date) {
        return orderService.getCountOfBranchTotalPriceByDate(date);
    }
    @GetMapping("/branch/{branchId}/count/totalPriceByday/{date}")
    double getCountOfEachBranchTotalPriceByDate(@PathVariable("date") String date, @PathVariable("branchId") short branchId) {
        return orderService.getCountOfEachBranchTotalPriceByDate(date, branchId);
    }

    @GetMapping("/branch/count/lastweek/{date}")
    public List<Object[]> getCountOfTotalPriceInBranchWeekly(@PathVariable("date") String date) {
        return orderService.getCountOfTotalPriceInBranchWeekly(date);
    }
    @GetMapping("/branch/{branchId}/count/lastweek/{date}")
    public List<Object[]> getCountOfTotalPriceEachBranchWeekly(@PathVariable("date") String date, @PathVariable("branchId") short branchId) {
        return orderService.getCountOfTotalPriceEachBranchWeekly(date, branchId);
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

    @GetMapping("/manager/{branchId}/revenue/currentWeek")
    public double getWeeklyRevenueInBranch(@PathVariable("branchId") short branchId) {
        return orderService.getWeeklyRevenueInBranch(branchId);
    }

    @GetMapping("/manager/revenue/currentMonth")
    public double getCurrentMonthRevenueEachBranch() {
        return orderService.getCurrentMonthRevenueEachBranch();
    }

    @GetMapping("/manager/{branchId}/revenue/currentMonth")
    public double getCurrentMonthRevenueInBranch(@PathVariable("branchId") short branchId) {
        return orderService.getCurrentMonthRevenueInBranch(branchId);
    }

    @GetMapping("/manager/compare/lastMonth")
    public double compareLastMonthBranchRevenue(){
        return orderService.compareLastMonthBranchRevenue();
    }

    @GetMapping("/manager/{branchId}/compare/lastMonth")
    public double compareLastMonthOfBranchRevenue( @PathVariable("branchId") short branchId){
        return orderService.compareLastMonthOfBranchRevenue(branchId);
    }

    @GetMapping("/manager/orderQuantity/monthly")
    public List<Object[]> getOrderQuantityByStatus(){
        return orderService.getOrderQuantityByStatus();
    }

    @GetMapping("/manager/{branchId}/orderQuantity/monthly")
    public List<Object[]> getOrderQuantityByStatusEachBranch(@PathVariable("branchId") short branchId){
        return orderService.getOrderQuantityByStatusEachBranch(branchId);
    }

    @GetMapping("/manager/topProducts/last3Months/{date}")
    public List<Object[]> getTopProductsLast3Months(@PathVariable("date") String date){
        return orderService.getTopProducts(date);
    }
    @GetMapping("/branch/{branchId}/topProducts/last3Months/{date}")
    public List<Object[]> getTopProductsLast3MonthsEachBranch(@PathVariable("date") String date,  @PathVariable("branchId") short branchId){
        return orderService.getTopProductsEachBranch(date, branchId);
    }
}