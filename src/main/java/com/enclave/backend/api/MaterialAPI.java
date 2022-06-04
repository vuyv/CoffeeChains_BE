package com.enclave.backend.api;

import com.enclave.backend.dto.InventoryDTO;
import com.enclave.backend.dto.MaterialDTO;
import com.enclave.backend.entity.DailyInventory;
import com.enclave.backend.entity.Inventory;
import com.enclave.backend.entity.Material;
import com.enclave.backend.service.DailyInventoryService;
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

    @Autowired
    private DailyInventoryService dailyInventoryService;

    @GetMapping("/all")
    public List<Material> getCategories() {
        return materialService.getAllMaterial();
    }

    @GetMapping("/{materialId}")
    public Material getMaterialById(@PathVariable("materialId") short materialId) {
        return materialService.getMaterialById(materialId);
    }

    @PostMapping("/new")
    public Material createMaterial(@RequestBody MaterialDTO materialDTO) {
        return materialService.createMaterial(materialDTO);
    }

    @PostMapping("/addToInventory")
    public ResponseEntity<String> addMaterials(@RequestBody List<InventoryDTO> inventoryDTOs) {
        return inventoryService.addMaterials(inventoryDTOs);
    }

    @GetMapping("/branch")
    public List<Inventory> getMaterialsByBranch() {
        return inventoryService.getMaterialsByBranch();
    }

    @GetMapping("/{materialId}/{unitId}")
    public Inventory getInventoryById(@PathVariable("materialId") short materialId, @PathVariable("unitId") short unitId) {
        return inventoryService.getInventoryById(materialId, unitId);
    }

    @PostMapping("/exportInventory")
    public ResponseEntity<DailyInventory> reduceMaterials(@RequestBody List<InventoryDTO> inventoryDTOs) {
        return dailyInventoryService.exportMaterials(inventoryDTOs);
    }

}
