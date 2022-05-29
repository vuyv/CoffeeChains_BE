package com.enclave.backend.api;

import com.enclave.backend.dto.InventoryDTO;
import com.enclave.backend.entity.Inventory;
import com.enclave.backend.entity.Material;
import com.enclave.backend.service.InventoryService;
import com.enclave.backend.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material")
@Slf4j
public class MaterialAPI {
    @Autowired
    private MaterialService materialService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/all")
    public List<Material> getCategories() {
        return materialService.getAllMaterial();
    }

    @GetMapping("/unit/{materialId}")
    public List<Object[]> getUnitByMaterial(@PathVariable("materialId") short materialId) {
        return materialService.getUnitByMaterial(materialId);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addMaterials(@RequestBody List<InventoryDTO> inventoryDTOs){
        return inventoryService.addMaterials(inventoryDTOs);
    }

    @GetMapping("/branch")
    public List<Inventory> getMaterialsByBranch(){
        return inventoryService.getMaterialsByBranch();
    }
}
