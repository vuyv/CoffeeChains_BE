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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        if (orderDTO.getDiscount_code() != "") {
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
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order Id: " + id));
    }

    @Override
    public Optional<Order> findOrderByOrdinalNumber(int ordinalNumber) {
        Date currentDate = new Date();
        StringBuilder key = orderIdGenerator.generateKey(employeeService.getCurrentEmployee().getBranch(), currentDate);
        String orderId = String.valueOf(key.append(String.format("%03d", ordinalNumber)));
        System.out.println(orderId);
        return orderRepository.findById(orderId);
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
        if (isSameDay(currentDate, oldOrder.getCreatedAt())) {
            if (oldOrder.getStatus().equals(Order.Status.CREATED)) {
                oldOrder.setStatus(Order.Status.CANCELED);
            }
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
//        System.out.println(currentDate);
//        System.out.println(order.getCreatedAt());
        if (isSameDay(currentDate, order.getCreatedAt())) {
            return order;
        }
//        System.out.println(isSameDay(currentDate, order.getCreatedAt()));
//        System.out.println(currentDate);
//        System.out.println(order.getCreatedAt());
        throw new IllegalArgumentException("Not permission!");
    }
}
