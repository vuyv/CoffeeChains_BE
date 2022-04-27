package com.enclave.backend.repository;

import com.enclave.backend.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeReportRepository extends JpaRepository<Order, String> {
    // Seller
    @Query(value = "SELECT employee.name, count(*) AS orderQuantity, sum(orders.total_price) as revenue FROM Orders JOIN employee ON orders.created_by = employee.id " +
            "where employee.branch_id = :branchId AND orders.created_at BETWEEN :startDate AND :endDate group by employee.name", nativeQuery = true)
    List<Object[]> getEachBranch(@Param("branchId") short branchId, @Param("startDate")String startDate, @Param("endDate")String endDate);
}
