package com.enclave.backend.service.impl;

import com.enclave.backend.converter.InventoryConverter;
import com.enclave.backend.dto.InventoryDTO;
import com.enclave.backend.entity.Inventory;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Unit;
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

    private Inventory existInventory(InventoryDTO inventoryDTO) {
        List<Inventory> inventoryList = getMaterialsByBranch();
        for (Inventory inventory : inventoryList) {
            if (inventory.getRawMaterial().getId() == inventoryDTO.getMaterialId() && inventory.getUnit().getId() ==
                    inventoryDTO.getUnitId()) {
                return inventory;
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<String> addMaterials(List<InventoryDTO> inventoryDTOs) {
        inventoryDTOs.forEach(inventoryDTO -> {
            if (Objects.nonNull(existInventory(inventoryDTO))) {
                Inventory inventory = existInventory(inventoryDTO);
                double quantity = inventory.getQuantity();
                inventory.setQuantity(quantity + inventoryDTO.getQuantity());
                inventoryRepository.save(inventory);
            }
            Inventory newInventory = inventoryConverter.toEntity(inventoryDTO);
            Material rawMaterial = materialService.getMaterialById(inventoryDTO.getMaterialId());
            newInventory.setRawMaterial(rawMaterial);
            Unit unit = unitService.findById(inventoryDTO.getUnitId());
            newInventory.setUnit(unit);
            newInventory.setQuantity(inventoryDTO.getQuantity());
            newInventory.setBranch(employeeService.getCurrentEmployee().getBranch());
            newInventory.setCreatedAt(new Date());
            inventoryRepository.save(newInventory);
        });
        return ResponseEntity.ok("");
    }

    @Override
    public List<Inventory> getMaterialsByBranch() {
        short branchId = employeeService.getCurrentEmployee().getBranch().getId();
        return inventoryRepository.getMaterialsByBranch(branchId);
    }
}
