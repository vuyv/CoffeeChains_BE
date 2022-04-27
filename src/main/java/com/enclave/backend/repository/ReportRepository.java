package com.enclave.backend.repository;

import com.enclave.backend.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Order, String> {
    //MANAGER
    @Query(value = "SELECT product.name, sum(order_detail.quantity) AS QUANTITY, sum(QUANTITY * product.price) as revenue FROM order_detail " +
            "JOIN orders ON orders.id = order_detail.order_id JOIN product ON order_detail.product_id = product.id " +
            "JOIN employee on orders.created_by = employee.id join category on product.category_id = category.id  " +
            "where employee.branch_id = :branchId and category_id = :categoryId AND orders.created_at BETWEEN :startDate AND :endDate group by product.name", nativeQuery = true)
    List<Object[]> getProductsByCategoryEachBranch(@Param("branchId") short branchId, @Param("categoryId") short categoryId, @Param("startDate")String startDate, @Param("endDate")String endDate);

    @Query(value = "SELECT product.name, sum(order_detail.quantity) AS QUANTITY, sum(QUANTITY * product.price) as revenue FROM order_detail\n" +
            "JOIN orders ON orders.id = order_detail.order_id JOIN product ON order_detail.product_id = product.id\n" +
            "JOIN employee on orders.created_by = employee.id join category on product.category_id = category.id \n" +
            "where employee.branch_id = :branchId AND orders.created_at BETWEEN :startDate AND :endDate group by product.name", nativeQuery = true)
    List<Object[]> getProductsAllCategoryEachBranch(@Param("branchId") short branchId, @Param("startDate")String startDate, @Param("endDate")String endDate );

    @Query(value = "SELECT employee.name, count(*) AS orderQuantity, sum(orders.total_price) as revenue FROM Orders JOIN employee ON orders.created_by = employee.id " +
            "where employee.branch_id = :branchId AND orders.created_at BETWEEN :startDate AND :endDate group by employee.name", nativeQuery = true)
    List<Object[]> getEmployeesEachBranch(@Param("branchId") short branchId, @Param("startDate")String startDate, @Param("endDate")String endDate);

}
