package com.enclave.backend.service.impl;

import com.enclave.backend.converter.OrderConverter;
import com.enclave.backend.converter.OrderDetailConverter;
import com.enclave.backend.dto.OrderDTO;
import com.enclave.backend.dto.OrderDetailDTO;
import com.enclave.backend.dto.ProductInOrderDTO;
import com.enclave.backend.entity.Discount;
import com.enclave.backend.entity.Order;
import com.enclave.backend.entity.OrderDetail;
import com.enclave.backend.entity.Product;
import com.enclave.backend.repository.OrderDetailRepository;
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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private OrderDetailRepository orderDetailRepository;

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
                if (productDB.getId() != productFE.getId()) {
                    return false;
                }
                if (productDB.getPrice() == productFE.getPrice()) {
                    return true;
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
    public Order findOrderByOrdinalNumber(int ordinalNumber) {
        List<Order> orders = orderRepository.findAll();
        Order foundOrder = new Order();
        orders.forEach(order -> {
            if (order.getId().endsWith(String.valueOf(ordinalNumber))) {
                foundOrder.setId(order.getId());
                foundOrder.setDiscount(order.getDiscount());
                foundOrder.setStatus(order.getStatus());
                foundOrder.setTotalPrice(order.getTotalPrice());
                foundOrder.setOrderDetails(order.getOrderDetails());
                foundOrder.setCreatedAt(order.getCreatedAt());
                foundOrder.setCanceledBy(order.getCanceledBy());
                foundOrder.setCreatedBy(order.getCreatedBy());
            } else {
                throw new EntityNotFoundException("Order not found");
            }
        });

        return foundOrder;
    }

    @Override
    public Order cancelOrder(Order order) {
        Order oldOrder = findOrderById(order.getId());
        oldOrder.setStatus(Order.Status.CANCELED);
        return orderRepository.save(oldOrder);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
