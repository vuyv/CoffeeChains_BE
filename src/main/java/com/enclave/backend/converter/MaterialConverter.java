package com.enclave.backend.converter;

import com.enclave.backend.dto.MaterialDTO;
import com.enclave.backend.entity.Material;
import com.enclave.backend.entity.Unit;
import com.enclave.backend.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MaterialConverter {
    @Autowired
    private UnitService unitService;

    public Material toEntity(MaterialDTO dto){
        Material entity = new Material();
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        List<Unit> units = new ArrayList<>();
        dto.getUnits().forEach(unitDTO -> {
            Unit unit = unitService.findById(unitDTO.getId());
            if (Objects.nonNull(unit)){
                units.add(unit);
            } else {
                Unit newUnit = unitService.createUnit(unitDTO);
                units.add(newUnit);
            }
        });
        entity.setUnits(units);

        return entity;
    }
}
