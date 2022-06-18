package com.enclave.backend.converter;

import com.enclave.backend.dto.UnitDTO;
import com.enclave.backend.entity.Unit;
import org.springframework.stereotype.Component;

@Component
public class UnitConverter {

    public Unit toEntity(UnitDTO dto){
        Unit entity = new Unit();
        entity.setUnit(dto.getUnit());
        entity.setRate(dto.getRate());
        entity.setNetWeight(dto.getNetWeight());
        return entity;
    }
}
