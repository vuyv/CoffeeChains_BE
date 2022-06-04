package com.enclave.backend.service.impl;

import com.enclave.backend.converter.InventoryConverter;
import com.enclave.backend.dto.InventoryDTO;
import com.enclave.backend.entity.Inventory;
import com.enclave.backend.entity.InventoryHistory;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Unit;
import com.enclave.backend.repository.InventoryHistoryRepository;
import com.enclave.backend.repository.InventoryRepository;
import com.enclave.backend.service.EmployeeService;
import com.enclave.backend.service.InventoryService;
import com.enclave.backend.service.MaterialService;
import com.enclave.backend.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private InventoryConverter inventoryConverter;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UnitService unitService;

    @Autowired
    private InventoryHistoryRepository inventoryHistoryRepository;

    @Override
    public Inventory getInventoryById(short materialId, short unitId) {
        short branchId = employeeService.getBranchOfCurrentEmployee().getId();
        return inventoryRepository.getInventoryById(branchId, materialId, unitId);
    }

    @Override
    public ResponseEntity<String> addMaterials(List<InventoryDTO> inventoryDTOs) {
        inventoryDTOs.forEach(inventoryDTO -> {
            Inventory inventory = getInventoryById(inventoryDTO.getMaterialId(), inventoryDTO.getUnitId());
            if (Objects.nonNull(inventory)) {
                double quantity = inventory.getQuantity();
                inventory.setQuantity(quantity + inventoryDTO.getQuantity());
                inventory.setCreatedAt(new Date());
                inventoryRepository.save(inventory);
            } else {
                Inventory newInventory = inventoryConverter.toEntity(inventoryDTO);
                Material rawMaterial = materialService.getMaterialById(inventoryDTO.getMaterialId());
                newInventory.setRawMaterial(rawMaterial);
                Unit unit = unitService.findById(inventoryDTO.getUnitId());
                newInventory.setUnit(unit);
                newInventory.setQuantity(inventoryDTO.getQuantity());
                newInventory.setBranch(employeeService.getCurrentEmployee().getBranch());
                newInventory.setCreatedAt(new Date());
                inventoryRepository.save(newInventory);
            }

            InventoryHistory inventoryHistory = new InventoryHistory();
            inventoryHistory.setQuantity(inventoryDTO.getQuantity());
            inventoryHistory.setRawMaterial(materialService.getMaterialById(inventoryDTO.getMaterialId()));
            inventoryHistory.setUnit(unitService.findById(inventoryDTO.getUnitId()));
            inventoryHistory.setBranch(employeeService.getCurrentEmployee().getBranch());
            inventoryHistory.setQuantity(inventoryDTO.getQuantity());
            inventoryHistoryRepository.save(inventoryHistory);
        });
        return ResponseEntity.ok("");
    }

    @Override
    public List<Inventory> getMaterialsByBranch() {
        short branchId = employeeService.getCurrentEmployee().getBranch().getId();
        return inventoryRepository.getMaterialsByBranch(branchId);
    }
}
