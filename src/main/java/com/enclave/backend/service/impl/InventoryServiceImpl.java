package com.enclave.backend.service.impl;

import com.enclave.backend.converter.InventoryConverter;
import com.enclave.backend.dto.InventoryDTO;
import com.enclave.backend.entity.Inventory;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.UnitConverter;
import com.enclave.backend.repository.InventoryRepository;
import com.enclave.backend.repository.MaterialRepository;
import com.enclave.backend.repository.UnitRepository;
import com.enclave.backend.service.EmployeeService;
import com.enclave.backend.service.InventoryService;
import com.enclave.backend.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private InventoryConverter inventoryConverter;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public ResponseEntity<String> addMaterials(List<InventoryDTO> inventoryDTOs) {
        inventoryDTOs.forEach(inventoryDTO -> {
            Inventory inventory = inventoryConverter.toEntity(inventoryDTO);
            Material rawMaterial = materialRepository.findById(inventoryDTO.getMaterialId()).get();
            inventory.setRawMaterial(rawMaterial);
            UnitConverter unitConverter = unitRepository.findById(inventoryDTO.getUnitId()).get();
            inventory.setUnit(unitConverter);
            inventory.setQuantity(inventoryDTO.getQuantity());
            inventory.setBranch(employeeService.getCurrentEmployee().getBranch());
            inventory.setCreatedAt(new Date());
            inventoryRepository.save(inventory);
        });
        return  ResponseEntity.ok("");
    }

    @Override
    public List<Inventory> getMaterialsByBranch() {
        short branchId = employeeService.getCurrentEmployee().getBranch().getId();
        return inventoryRepository.getMaterialsByBranch(branchId);
    }
}
