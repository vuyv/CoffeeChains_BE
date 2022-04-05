package com.enclave.backend.repository;

import com.enclave.backend.entity.Discount;
import com.enclave.backend.entity.Employee;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Enumerated;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, String> {
    @Query("SELECT e FROM Discount e WHERE e.status= :status")
    List<Discount> getDiscountsByStatus(@Param("status") Discount.Status status );
}
