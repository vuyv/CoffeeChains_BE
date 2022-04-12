package com.enclave.backend.repository;

import com.enclave.backend.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o WHERE o.createdBy.branch.id= :branchId")
    List<Order> findByBranch(@Param("branch") short branchId);

    @Query("SELECT o FROM Order o WHERE o.createdBy.branch.id= :branchId AND o.id = :id")
    Order findOrderByIdInBranch(@Param("branch") short branchId, @Param("id") String id);

    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findOrdersBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
