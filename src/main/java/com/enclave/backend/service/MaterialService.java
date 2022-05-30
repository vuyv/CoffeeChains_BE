package com.enclave.backend.service;

import com.enclave.backend.dto.MaterialDTO;
import com.enclave.backend.entity.Material;

import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterial();

    Material getMaterialById(short materialId);

    Material createMaterial(MaterialDTO dto);
}
