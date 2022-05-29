package com.enclave.backend.service.impl;

import com.enclave.backend.entity.Material;
import com.enclave.backend.repository.MaterialRepository;
import com.enclave.backend.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAllMaterial() {
        return materialRepository.findAll() ;
    }

    @Override
    public List<Object[]> getUnitByMaterial(short materialId) {
        return materialRepository.getUnitByMaterial(materialId);
    }

}
