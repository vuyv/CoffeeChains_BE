package com.enclave.backend.service.impl;

import com.enclave.backend.dto.InventoryDTO;
import com.enclave.backend.entity.DailyInventory;
import com.enclave.backend.entity.Inventory;
import com.enclave.backend.repository.DailyInventoryRepository;
import com.enclave.backend.repository.InventoryRepository;
import com.enclave.backend.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.enclave.backend.entity.DateUtil.*;

@Service
@Slf4j
public class DailyInventoryServiceImpl implements DailyInventoryService {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private DailyInventoryRepository dailyInventoryRepository;

    @Override
    public ResponseEntity<DailyInventory> exportMaterials(List<InventoryDTO> dailyInventoryDTOs) {
        List<DailyInventory> exportList = new ArrayList<>();
        for (InventoryDTO dailyInventoryDTO : dailyInventoryDTOs) {
            Inventory inventory = inventoryService.getInventoryById(dailyInventoryDTO.getMaterialId(), dailyInventoryDTO.getUnitId());

            if (Objects.isNull(inventory)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (inventory.getQuantity() < dailyInventoryDTO.getQuantity()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            DailyInventory newDailyInventory = new DailyInventory();
            newDailyInventory.setQuantity(dailyInventoryDTO.getQuantity());
            newDailyInventory.setRawMaterial(materialService.getMaterialById(dailyInventoryDTO.getMaterialId()));
            newDailyInventory.setUnit(unitService.findById(dailyInventoryDTO.getUnitId()));
            newDailyInventory.setBranch(employeeService.getCurrentEmployee().getBranch());
            newDailyInventory.setQuantity(dailyInventoryDTO.getQuantity());
            exportList.add(newDailyInventory);

            dailyInventoryRepository.saveAll(exportList);
            double quantityInStock = inventory.getQuantity();
            inventory.setQuantity(quantityInStock - dailyInventoryDTO.getQuantity());
            inventoryRepository.save(inventory);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public List<DailyInventory> getDailyInventories() {
        Date currentDate = new Date();
        String startDate = startOfDay(currentDate).toString();
        String endDate = endOfDay((currentDate)).toString();
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();

        List<DailyInventory> dailyInventoryList = dailyInventoryRepository.getDailyInventoryByBranchByTime(branchId, startDate, endDate);
        return dailyInventoryList;
    }

    @Override
    public List<DailyInventory> getExportMaterials(short materialId, short unitId) {
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return dailyInventoryRepository.getExportMaterials(branchId, materialId, unitId);
    }

    @Override
    public List<Object[]> countDailyQuantityByTime() {
        Date currentDate = new Date();
        String startDate = startOfDay(currentDate).toString();
        String endDate = endOfDay((currentDate)).toString();
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return dailyInventoryRepository.countQuantityByTime(branchId, startDate, endDate);
    }

    @Override
    public List<Object[]> countWeeklyQuantityByTime() {
        Date currentDate = new Date();
        String startDate = startOfWeek(currentDate).toString();
        String endDate = endOfWeek((currentDate)).toString();
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return dailyInventoryRepository.countQuantityByTime(branchId, startDate, endDate);
    }

    @Override
    public List<Object[]> countMonthlyQuantityByTime() {
        Date currentDate = new Date();
        String startDate = startOfMonth(currentDate).toString();
        String endDate = endOfMonth((currentDate)).toString();
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return dailyInventoryRepository.countQuantityByTime(branchId, startDate, endDate);
    }

    @Override
    public List<DailyInventory> getDailyInventoryByTime(String date) {
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return dailyInventoryRepository.getDailyInventoryByTime(branchId, date);
    }
}
