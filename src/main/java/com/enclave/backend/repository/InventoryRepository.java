package com.enclave.backend.repository;

import com.enclave.backend.entity.Inventory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Short> {

    @Query("SELECT i FROM Inventory i WHERE i.branch.id = :branchId")
    List<Inventory> getMaterialsByBranch(@Param("branchId")short branchId);
}
