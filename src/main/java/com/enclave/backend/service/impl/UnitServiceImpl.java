package com.enclave.backend.service.impl;

import com.enclave.backend.converter.UnitConverter;
import com.enclave.backend.dto.UnitDTO;
import com.enclave.backend.entity.Unit;
import com.enclave.backend.repository.UnitRepository;
import com.enclave.backend.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitConverter unitConverter;

    @Override
    public Unit findById(short id) {
        Optional<Unit> opUnit = unitRepository.findById(id);
        Unit unit = null;
        if (opUnit.isPresent()) {
            unit = opUnit.get();
        }
        return unit;
    }

    @Override
    public Unit createUnit(UnitDTO unitDTO) {
        Unit unit = unitConverter.toEntity(unitDTO);
        return unitRepository.save(unit);
    }

    @Override
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

}
