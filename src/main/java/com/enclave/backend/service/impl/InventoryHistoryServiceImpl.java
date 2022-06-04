package com.enclave.backend.service.impl;

import com.enclave.backend.entity.InventoryHistory;
import com.enclave.backend.repository.InventoryHistoryRepository;
import com.enclave.backend.service.EmployeeService;
import com.enclave.backend.service.InventoryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.enclave.backend.entity.DateUtil.*;

@Service
public class InventoryHistoryServiceImpl implements InventoryHistoryService {

    @Autowired
    private InventoryHistoryRepository inventoryHistoryRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<Object[]> countDailyQuantityByTime() {
        Date currentDate = new Date();
        String startDate = startOfDay(currentDate).toString();
        String endDate = endOfDay((currentDate)).toString();
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return inventoryHistoryRepository.countQuantityByTime(branchId, startDate, endDate);
    }

    @Override
    public List<Object[]> countWeeklyQuantityByTime() {
        Date currentDate = new Date();
        String startDate = startOfWeek(currentDate).toString();
        String endDate = endOfWeek((currentDate)).toString();
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return inventoryHistoryRepository.countQuantityByTime(branchId, startDate, endDate);
    }

    @Override
    public List<Object[]> countMonthlyQuantityByTime() {
        Date currentDate = new Date();
        String startDate = startOfMonth(currentDate).toString();
        String endDate = endOfMonth((currentDate)).toString();
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return inventoryHistoryRepository.countQuantityByTime(branchId, startDate, endDate);
    }

    @Override
    public List<InventoryHistory> getInventoryHistoryByTime(String time) {
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return inventoryHistoryRepository.getInventoryHistoryByTime(branchId, time);
    }
}
