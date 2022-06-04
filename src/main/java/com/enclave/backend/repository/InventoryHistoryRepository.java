package com.enclave.backend.repository;

import com.enclave.backend.entity.InventoryHistory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, Short> {

    @Query(value = "SELECT created_at, sum(quantity) FROM inventory_history i where i.branch_id = :branchId and i.created_at between :startDate and :endDate group by created_at", nativeQuery = true)
    List<Object[]> countQuantityByTime(@Param("branchId") short branchId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT * FROM inventory_history i where i.branch_id = :branchId and i.created_at = :date", nativeQuery = true)
    List<InventoryHistory> getInventoryHistoryByTime(@Param("branch") short branchId, @Param("time") String date);
}
