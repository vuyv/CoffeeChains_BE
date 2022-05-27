package com.enclave.backend.api;

import com.enclave.backend.entity.Material;
import com.enclave.backend.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/material")
public class MaterialAPI {
    @Autowired
    private MaterialService materialService;
    @GetMapping("/all")
    public List<Material> getCategories() {
        return materialService.getAllMaterial();
    }

    @GetMapping("/unit/{materialId}")
    public List<Object[]> getUnitByMaterial(@PathVariable("materialId") short materialId) {
        return materialService.getUnitByMaterial(materialId);
    }
}
