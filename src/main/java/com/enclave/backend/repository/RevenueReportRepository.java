package com.enclave.backend.repository;

import com.enclave.backend.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RevenueReportRepository extends JpaRepository<Order, String> {
    //OWNER
    @Query(value = "SELECT branch.name, branch.address, count(*) AS orderQuantity, sum(orders.total_price) as revenue FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND orders.created_at BETWEEN :startDate AND :endDate group by branch.name;\n", nativeQuery = true)
    List<Object[]> getAllBranch(@Param("startDate")String startDate, @Param("endDate")String endDate );

}
