package com.enclave.backend.converter;

import com.enclave.backend.dto.MaterialDTO;
import com.enclave.backend.dto.UnitDTO;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Unit;
//import com.enclave.backend.entity.UnitMaterial;
import com.enclave.backend.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialConverter {
    @Autowired
    private UnitService unitService;
    public Material toEntity(MaterialDTO dto){
        Material entity = new Material();
        entity.setName(dto.getName());

        List<Unit> units = new ArrayList<>();
        dto.getUnits().forEach(unitDTO -> {
            Unit unit = unitService.findById(unitDTO.getId());
            units.add(unit);
        });
        entity.setUnits(units);

        return entity;
    }
}
