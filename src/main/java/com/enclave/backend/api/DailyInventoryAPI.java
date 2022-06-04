package com.enclave.backend.api;

import com.enclave.backend.entity.DailyInventory;
import com.enclave.backend.service.DailyInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dailyInventory")
public class DailyInventoryAPI {
    @Autowired
    private DailyInventoryService dailyInventoryService;

    @GetMapping("/dailyInventories")
    public List<DailyInventory> getDailyInventories() {
        return dailyInventoryService.getDailyInventories();
    }

    @GetMapping("/daily/{materialId}/{unitId}")
    public List<DailyInventory> getExportMaterials(@PathVariable("materialId") short materialId, @PathVariable("unitId") short unitId) {
        return dailyInventoryService.getExportMaterials(materialId, unitId);
    }

    @GetMapping("/daily/countQuantity")
    public List<Object[]> countDailyQuantityByTime() {
        return dailyInventoryService.countDailyQuantityByTime();
    }

    @GetMapping("/weekly/countQuantity")
    public List<Object[]> countWeeklyQuantityByTime() {
        return dailyInventoryService.countWeeklyQuantityByTime();
    }

    @GetMapping("/monthly/countQuantity")
    public List<Object[]> countMonthlyQuantityByTime() {
        return dailyInventoryService.countMonthlyQuantityByTime();
    }

    @GetMapping("/{time}")
    public List<DailyInventory> getDailyInventoryByTime(@PathVariable("time") String time) {
        return dailyInventoryService.getDailyInventoryByTime(time);
    }
}
