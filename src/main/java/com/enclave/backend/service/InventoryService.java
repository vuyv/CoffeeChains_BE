package com.enclave.backend.service;

import com.enclave.backend.dto.InventoryDTO;
import com.enclave.backend.entity.Inventory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {

    ResponseEntity<String> addMaterials(List<InventoryDTO> inventoryDTOs);

    List<Inventory> getMaterialsByBranch();

    Inventory getInventoryById(short materialId, short unitId);
}
