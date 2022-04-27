package com.enclave.backend.repository;

import com.enclave.backend.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductReportRepository extends JpaRepository<Order, String> {
    //AllProduct
    @Query(value = "select category.name, sum(order_detail.quantity), sum(order_detail.quantity*product.price) from orders JOIN order_detail ON orders.id = order_detail.order_id JOIN product ON order_detail.product_id = product.id JOIN category ON product.category_id = category.id WHERE orders.created_at BETWEEN :startDate AND :endDate group by category.name;", nativeQuery = true)
    List<Object[]> getAllCategory(@Param("startDate")String startDate, @Param("endDate")String endDate);

    //ProductEachCategory
    @Query(value = "select product.name, sum(order_detail.quantity), sum(order_detail.quantity*product.price) from orders JOIN order_detail ON orders.id = order_detail.order_id JOIN product ON order_detail.product_id = product.id JOIN category ON product.category_id = category.id WHERE orders.created_at BETWEEN :startDate AND :endDate AND category.id = :categoryId group by product.name;", nativeQuery = true)
    List<Object[]> getEachCategory(@Param("categoryId") short categoryId, @Param("startDate")String startDate, @Param("endDate")String endDate );
}
