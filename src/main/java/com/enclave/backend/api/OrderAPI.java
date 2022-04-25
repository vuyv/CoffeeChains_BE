package com.enclave.backend.api;

import com.enclave.backend.dto.OrderDTO;
import com.enclave.backend.entity.Order;
import com.enclave.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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
    public List<Order> findOrdersInAMonthInBranch(){
        return orderService.findOrdersInAMonthInBranch();
    }

    //manager
    @GetMapping("/branch/count/orderByday/{date}")
    public Integer getCountOfBranchOrderByDate(@PathVariable ("date") String date){
        return orderService.getCountOfBranchOrderByDate(date);
    }

    @GetMapping("/branch/count/totalPriceByday/{date}")
    double getCountOfBranchTotalPriceByDate(@PathVariable ("date") String date){
        return orderService.getCountOfBranchTotalPriceByDate(date);
    }
    @GetMapping("/branch/count/lastweek")
    public List<Object[]> getCountOfTotalPriceInBranchWeekly(){
        return orderService.getCountOfTotalPriceInBranchWeekly();
    }
    @GetMapping("/manager/bestSellingProducts")
    public List<Object[]> getBestSellingProducts(){
        return orderService.getBestSellingProducts();
    }

    //owner
    @GetMapping("/owner/count/orderByday/{date}")
    public Integer getCountOfAllOrderByDate(@PathVariable("date") String date){
        return orderService.getCountOfAllOrderByDate(date);
    }

    @GetMapping("/owner/count/totalPriceByday/{date}")
    public double getCountOfAllTotalPriceByDate(@PathVariable("date") String date){
        return orderService.getCountOfAllTotalPriceByDate(date);
    }

    @GetMapping("/owner/count/ordersByday/eachBranch/{date}")
    public List<Object[]> getCountOfOrderEachBranch(@PathVariable("date") String date){
        return orderService.getCountOfOrderEachBranch(date);
    }

    @GetMapping("/owner/count/totalPriceByday/eachBranch/{date}")
    public List<Object[]> getCountOfTotalPriceEachBranch(@PathVariable("date") String date){
        return orderService.getCountOfTotalPriceEachBranch(date);
    }

    @GetMapping("/owner/compare/lastMonth")
    public double compareLastMonthRevenue(){
        return orderService.compareLastMonthRevenue();
    }

    @GetMapping("/owner/compare/lastWeek")
    public double compareLastWeekRevenue(){
        return orderService.compareLastWeekRevenue();
    }

    @GetMapping("/owner/topSeller/weekly")
    public List<Object[]> topWeeklySeller(){
        return orderService.topWeeklySeller();
    }

    @GetMapping("/owner/revenue/currentMonth")
    public double getCurrentMonthRevenue(){
        return orderService.getCurrentMonthRevenue();
    }

    @GetMapping("/owner/revenue/currentWeek")
    public double getCurrentWeekRevenue(){
        return orderService.getCurrentWeekRevenue();
    }

    //REPORT
    //REVENUE
    //AllBranch
    @GetMapping("/owner/report/revenue/daily/allBranch")
    public List<Object[]> getDailyRevenueAllBranch(){
        return orderService.getDailyRevenueAllBranch();
    }

    @GetMapping("/owner/report/revenue/weekly/allBranch")
    public List<Object[]> getWeeklyRevenueAllBranch(){
        return orderService.getWeeklyRevenueAllBranch();
    }

    @GetMapping("/owner/report/revenue/monthly/allBranch")
    public List<Object[]> getMonthlyRevenueAllBranch(){
        return orderService.getMonthlyRevenueAllBranch();
    }

    //PRODUCT
    //AllCategory
    @GetMapping("/owner/report/product/daily/allCategory")
    public List<Object[]> getDailyProductAllCategory(){
        return orderService.getDailyProductAllCategory();
    }

    @GetMapping("/owner/report/product/weekly/allCategory")
    public List<Object[]> getWeeklyProductAllCategory(){
        return orderService.getWeeklyProductAllCategory();
    }

    @GetMapping("/owner/report/product/monthly/allCategory")
    public List<Object[]> getMonthlyProductAllCategory(){
        return orderService.getMonthlyProductAllCategory();
    }

    //EachCategory
    @GetMapping("/owner/report/product/daily/category/{id}")
    public List<Object[]> getDailyProductByCategory(@PathVariable("id") short id){
        return orderService.getDailyProductByCategory(id);
    }

    @GetMapping("/owner/report/product/weekly/category/{id}")
    public List<Object[]> getWeeklyProductByCategory(@PathVariable("id") short id){
        return orderService.getWeeklyProductByCategory(id);
    }

    @GetMapping("/owner/report/product/monthly/category/{id}")
    public List<Object[]> getMonthlyProductByCategory(@PathVariable("id") short id){
        return orderService.getMonthlyProductByCategory(id);
    }

    //CUSTOM DATE
    @GetMapping("/owner/report/revenue/allBranch/customDaily/{date}")
    public List<Object[]> getCustomDailyRevenueAllBranch(@PathVariable("date") String date){
        return orderService.getCustomDailyRevenueAllBranch(date);
    }

    @GetMapping("/owner/report/revenue/allBranch/customWeekly/{date}")
    public List<Object[]> getCustomWeeklyRevenueAllBranch(@PathVariable("date") String date){
        return orderService.getCustomWeeklyRevenueAllBranch(date);
    }

    @GetMapping("/owner/report/revenue/allBranch/customMonthly/{date}")
    public List<Object[]> getCustomMonthlyRevenueAllBranch(@PathVariable("date") String date){
        return orderService.getCustomMonthlyRevenueAllBranch(date);
    }
}
