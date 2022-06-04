package com.enclave.backend.api;

import com.enclave.backend.entity.InventoryHistory;
import com.enclave.backend.service.InventoryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventoryHistory")
public class InventoryHistoryAPI {
    @Autowired
    private InventoryHistoryService inventoryHistoryService;

    @GetMapping("/daily/countQuantity")
    public List<Object[]> countDailyQuantityByTime(){
        return inventoryHistoryService.countDailyQuantityByTime();
    }

    @GetMapping("/weekly/countQuantity")
    public List<Object[]> countWeeklyQuantityByTime(){
        return inventoryHistoryService.countWeeklyQuantityByTime();
    }

    @GetMapping("/monthly/countQuantity")
    public List<Object[]> countMonthlyQuantityByTime(){
        return inventoryHistoryService.countMonthlyQuantityByTime();
    }

    @GetMapping("/{time}")
    public List<InventoryHistory> getDailyInventoryByTime(@PathVariable("time")String time){
        return inventoryHistoryService.getInventoryHistoryByTime( time);
    }
}
