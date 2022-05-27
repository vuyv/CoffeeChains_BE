package com.enclave.backend.service;

import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.UnitConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterial();

    List<Object[]> getUnitByMaterial(short materialId);
}
