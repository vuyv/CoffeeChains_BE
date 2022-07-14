package com.enclave.backend.repository;

import com.enclave.backend.entity.DailyInventory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DailyInventoryRepository extends JpaRepository<DailyInventory, Short> {

    @Query(value = "SELECT * FROM daily_inventory d where d.branch_id = :branchId and d.updated_at between :startDate and :endDate", nativeQuery = true)
    List<DailyInventory> getDailyInventoryByBranchByTime(@Param("branch") short branchId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT d FROM DailyInventory d WHERE d.rawMaterial.id = :materialId and d.unit.id = :unitId and d.branch.id = :branchId")
    List<DailyInventory> getExportMaterials(@Param("branchId") short branchId, @Param("materialId") short materialId, @Param("unitId") short unitId);

    @Query(value = "SELECT updated_at, sum(quantity) FROM daily_inventory d where d.branch_id = :branchId and d.updated_at between :startDate and :endDate group by updated_at order by updated_at", nativeQuery = true)
    List<Object[]> countQuantityByTime(@Param("branchId") short branchId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT * FROM daily_inventory d where d.branch_id = :branchId and d.updated_at = :date", nativeQuery = true)
    List<DailyInventory> getDailyInventoryByTime(@Param("branch") short branchId, @Param("date") String date);
}
