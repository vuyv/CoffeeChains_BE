package com.enclave.backend.service.impl;

import com.enclave.backend.converter.OrderConverter;
import com.enclave.backend.converter.OrderDetailConverter;
import com.enclave.backend.dto.OrderDTO;
import com.enclave.backend.dto.OrderDetailDTO;
import com.enclave.backend.dto.ProductInOrderDTO;
import com.enclave.backend.entity.*;
import com.enclave.backend.repository.OrderRepository;
import com.enclave.backend.repository.ProductRepository;
import com.enclave.backend.service.DiscountService;
import com.enclave.backend.service.OrderIdGenerator;
import com.enclave.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.enclave.backend.entity.DateUtil.*;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderIdGenerator orderIdGenerator;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private OrderDetailConverter orderDetailConverter;

    @Autowired
    private DateUtil dateUtil;

    private List<ProductInOrderDTO> getListProductInFE(List<OrderDetailDTO> orderDetailDTOs) {
        List<ProductInOrderDTO> productsInFE = new ArrayList<>();
        orderDetailDTOs.forEach(orderDetailDTO -> {
            productsInFE.add(orderDetailDTO.getProduct());
        });
        return productsInFE;
    }

    private List<Product> getListProductInDB(List<OrderDetailDTO> orderDetailDTOs) {
        List<ProductInOrderDTO> productsInFE = getListProductInFE(orderDetailDTOs);
        List<Short> ids = new ArrayList<>();
        productsInFE.forEach(productInOrderDTO -> ids.add(productInOrderDTO.getId()));
        List<Product> productsInDB = productRepository.findByIdIn(ids);
        return productsInDB;
    }

    private boolean isValidProduct(OrderDTO orderDTO) {
        List<Product> productsInDB = getListProductInDB(orderDTO.getOrderDetails());
        List<ProductInOrderDTO> productsInFE = getListProductInFE(orderDTO.getOrderDetails());
        if (productsInFE.size() != productsInDB.size()) {
            return false;
        }
        for (Product productDB : productsInDB) {
            for (ProductInOrderDTO productFE : productsInFE) {
                if (productDB.getId() == productFE.getId()) {
                    return productDB.getPrice() == productFE.getPrice();
                }
            }
        }
        return false;
    }

    private double applyDiscountCode(double total, String discountCode, Date currentDate) {
        Discount discount = discountService.getDiscountWithDate(discountCode, currentDate);
        if (!discountService.isValidDiscount(discountCode, currentDate)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
            return total;
        }
        total -= total * discount.getPercent() / 100;
        ResponseEntity.status(HttpStatus.OK);
        return total;
    }

    private double calculateTotal(List<OrderDetailDTO> orderDetailDTOS, List<Product> productsInDB) {
        double total = 0;
        for (Product product : productsInDB) {
            for (OrderDetailDTO orderDetailDTO : orderDetailDTOS) {
                if (product.getId() == orderDetailDTO.getProduct().getId()) {
                    total += product.getPrice() * orderDetailDTO.getQuantity();
                }
            }
        }
        return total;
    }

    @Override
    public ResponseEntity<Order> createNewOrder(OrderDTO orderDTO) {
        if (!isValidProduct(orderDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Invalid product").body(null);
        }

        double total = calculateTotal(orderDTO.getOrderDetails(), getListProductInDB(orderDTO.getOrderDetails()));
        Date date = new Date();
        Order newOrder = orderConverter.toEntity(orderDTO);
        String orderId = orderIdGenerator.createOrderIdForBranch(date);
        newOrder.setId(orderId);
        newOrder.setCreatedAt(date);
        newOrder.setCreatedBy(employeeService.getCurrentEmployee());
        newOrder.setStatus(Order.Status.CREATED);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDTO.getOrderDetails().forEach(orderDetailDTO -> {
            OrderDetail orderDetail = orderDetailConverter.toEntity(orderDetailDTO);
            orderDetail.setOrder(newOrder);
            orderDetails.add(orderDetail);
        });
        newOrder.setOrderDetails(orderDetails);

        if (orderDTO.getDiscount_code() != "" ) {
            total = applyDiscountCode(total, orderDTO.getDiscount_code(), date);
            if (isValidTotal(orderDTO, total)) {
                newOrder.setDiscount(discountService.getDiscountByCode(orderDTO.getDiscount_code()));
                newOrder.setTotalPrice(total);
                orderRepository.save(newOrder);
                return ResponseEntity.status(HttpStatus.OK).header("Created order successful").body(newOrder);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Invalid total money").body(null);
        }

        if (isValidTotal(orderDTO, total)) {
            newOrder.setTotalPrice(total);
            orderRepository.save(newOrder);
            return ResponseEntity.status(HttpStatus.OK).header("Created order successful").body(newOrder);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Invalid total money").body(null);
    }

    @Override
    public boolean isValidTotal(OrderDTO orderDTO, double total) {
        return orderDTO.getTotalPrice() == total;
    }

    @Override
    public Order findOrderById(String id) {
        Optional<Order> orderOptional =  orderRepository.findById(id);
        Order order = null;
        if (orderOptional.isPresent()){
            order = orderOptional.get();
        }
        return order;
    }

    @Override
    public Order findOrderByOrdinalNumber(int ordinalNumber) {
        Date currentDate = new Date();
        StringBuilder key = orderIdGenerator.generateKey(employeeService.getCurrentEmployee().getBranch(), currentDate);
        String orderId = String.valueOf(key.append(String.format("%03d", ordinalNumber)));
        Optional<Order> orderOptional =  orderRepository.findById(orderId);
        Order order = null;
        if (orderOptional.isPresent()){
           order = orderOptional.get();
        }
        return order;
    }

    public boolean isSameDay(Date date1, Date date2) {
        Instant instant1 = date1.toInstant().truncatedTo(ChronoUnit.DAYS);
        Instant instant2 = date2.toInstant().truncatedTo(ChronoUnit.DAYS);
        return instant1.equals(instant2);
    }

    @Override
    public Order cancelOrder(String id) {
        Order oldOrder = findOrderById(id);
        Date currentDate = new Date();
        if (isSameDay(currentDate, oldOrder.getCreatedAt()) && oldOrder.getStatus().equals(Order.Status.CREATED)) {
            oldOrder.setStatus(Order.Status.CANCELED);
            oldOrder.setCanceledBy(employeeService.getCurrentEmployee());
        }
        return orderRepository.save(oldOrder);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersInBranch() {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();
        return orderRepository.findByBranch(branchId);
    }

    @Override
    public Order findOrderByIdInBranch(short branchId, String orderId) {
        Date currentDate = new Date();
        Order order = orderRepository.findOrderByIdInBranch(branchId, orderId);
        return order;
    }

    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public List<Order> getDailyOrdersInBranch(String date) {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();
        return orderRepository.getDailyOrdersInBranch(branchId, StringtoDate(date) );
    }

    @Override
    public List<Order> getWeeklyOrdersInBranch(String date) {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();

        Date selectedDate = StringtoDate(date);
        String startDate = startOfWeek(selectedDate).toString();
        String endDate = endOfWeek((selectedDate)).toString();

        return orderRepository.getOrdersInBranchByTime(branchId, StringtoDate(startDate), StringtoDate(endDate)  );
    }

    @Override
    public List<Order> getMonthlyOrdersInBranch(String date) {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();

        Date selectedDate = StringtoDate(date);
        String startDate = startOfMonth(selectedDate).toString();
        String endDate = endOfMonth((selectedDate)).toString();

        return orderRepository.getOrdersInBranchByTime(branchId, StringtoDate(startDate), StringtoDate(endDate)  );
    }

    //manager
    //Chart: weekly revenue
    @Override
    public List<Object[]> getCountOfTotalPriceInBranchWeekly(String date) {

        Date selectedDate = StringtoDate(date);
        String startDate = startOfWeek(selectedDate).toString();
        String endDate = endOfWeek((selectedDate)).toString();

        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();

        List<Object[]> queryResult = orderRepository.getCountOfTotalPriceInBranchWeekly(branchId, startDate,endDate );
        return queryResult;
    }

    @Override
    public int getCountOfBranchOrderByDate(String date) {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();
        int count = 0;
        try {
            count = orderRepository.getCountOfBranchOrderByDate(branchId,new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (Exception e){
            System.out.println(e);
        }
        return count;
    }

    @Override
    public double getCountOfBranchTotalPriceByDate(String date) {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();
        double count = 0;
        try {
            count = orderRepository.getCountOfBranchTotalPriceByDate(branchId,new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (Exception e){
            System.out.println(e);
        }
        return count;
    }

    //owner
    @Override
    public double getCountOfAllTotalPriceByDate(String date) {
        double count = 0;
        try {
            count = orderRepository.getCountOfAllTotalPriceByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (Exception e){
            System.out.println(e);
        }
    return count;
    }

    @Override
    public Integer getCountOfAllOrderByDate(String date) {
        int count = 0;
        try {
            count = orderRepository.getCountOfAllOrderByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (Exception e){
            System.out.println(e);
        }
        return count;
    }

    @Override
    public List<Object[]> getCountOfOrderEachBranch(String date) {
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            queryResult = orderRepository.getCountOfOrderEachBranch(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getCountOfTotalPriceEachBranch(String date) {
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            queryResult = orderRepository.getCountOfTotalPriceEachBranch(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public double compareLastMonthRevenue() {
        double lastMonthRevenue = orderRepository.getLastMonthRevenue();
        double currentMonthRevenue = orderRepository.getCurrentMonthRevenue();
        double compare = currentMonthRevenue - lastMonthRevenue;
        return compare;
    }

    @Override
    public double compareLastWeekRevenue() {
        double lastWeekRevenue = orderRepository.getLastWeekRevenue();
        double currentWeekRevenue = orderRepository.getCurrentWeekRevenue();
        double compare = currentWeekRevenue - lastWeekRevenue;
        return compare;
    }

    @Override
    public List<Object[]> topWeeklySeller() {
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            queryResult = orderRepository.topWeeklySeller();
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;    }

    @Override
    public double getCurrentMonthRevenue() {
        return orderRepository.getCurrentMonthRevenue();
    }

    @Override
    public double getCurrentWeekRevenue() {
        return orderRepository.getCurrentWeekRevenue();
    }

    @Override
    public List<Object[]> getBestSellingProducts() {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();

        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            queryResult = orderRepository.getBestSellingProducts(branchId);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public double getWeeklyRevenueEachBranch() {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();
        return orderRepository.getWeeklyRevenueEachBranch(branchId);
    }

    @Override
    public double getCurrentMonthRevenueEachBranch() {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();
        return orderRepository.getCurrentMonthRevenueEachBranch(branchId);
    }

    @Override
    public double compareLastMonthBranchRevenue() {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();
        double lastMonthRevenue =  orderRepository.getLastMonthRevenueEachBranch(branchId);
        double currentMonthRevenue = orderRepository.getCurrentMonthRevenueEachBranch(branchId);
        double compare = currentMonthRevenue - lastMonthRevenue;
        return compare;
    }

    @Override
    public List<Object[]> getOrderQuantityByStatus() {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();
        return orderRepository.getMonthlyOrderQuantityBranchBothStatus(branchId);
    }

    @Override
    public List<Object[]> getTopProducts(String date) {
        Employee employee = employeeService.getCurrentEmployee();
        short branchId = employee.getBranch().getId();
        Date selectedDate = StringtoDate(date);
        String startDate = startOfLast3Months(selectedDate).toString();
        String endDate = endOfLast3Months(selectedDate).toString();

        System.out.println("START 3 MONTH: " + startDate + "END 3 MONTH: " + endDate);
        return orderRepository.getTopProducts(branchId, startDate, endDate);
    }
}