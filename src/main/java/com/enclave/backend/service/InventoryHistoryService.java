package com.enclave.backend.service;

import com.enclave.backend.entity.InventoryHistory;

import java.util.List;

public interface InventoryHistoryService {

    List<Object[]> countDailyQuantityByTime();

    List <Object[]> countWeeklyQuantityByTime();

    List <Object[]> countMonthlyQuantityByTime();

    List <InventoryHistory> getInventoryHistoryByTime(String time);
}
