package com.enclave.backend.service.impl;

import com.enclave.backend.converter.MaterialConverter;
import com.enclave.backend.dto.MaterialDTO;
import com.enclave.backend.entity.Material;
import com.enclave.backend.repository.MaterialRepository;
import com.enclave.backend.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialConverter materialConverter;
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAllMaterial() {
        return materialRepository.findAll() ;
    }

    @Override
    public Material getMaterialById(short materialId) {
        return materialRepository.findById(materialId).orElseThrow(()-> new IllegalArgumentException("Invalid Material id: " + materialId));
    }

    @Override
    public Material createMaterial(MaterialDTO dto) {
        Material newMaterial = materialConverter.toEntity(dto);
        return materialRepository.save(newMaterial);
    }

}
