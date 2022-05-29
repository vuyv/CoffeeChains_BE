package com.enclave.backend.service;

import com.enclave.backend.entity.Material;

import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterial();

    List<Object[]> getUnitByMaterial(short materialId);
}
