package com.enclave.backend.service;

import com.enclave.backend.dto.InventoryDTO;
import com.enclave.backend.entity.DailyInventory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DailyInventoryService {
    ResponseEntity<DailyInventory> exportMaterials(List<InventoryDTO> inventoryDTOs);

    List<DailyInventory> getDailyInventories();

    List<DailyInventory> getExportMaterials(short materialId, short unitId);

    List <Object[]> countDailyQuantityByTime();

    List <Object[]> countWeeklyQuantityByTime();

    List <Object[]> countMonthlyQuantityByTime();

    List <DailyInventory> getDailyInventoryByTime(String date);
}
