package com.enclave.backend.service;

import com.enclave.backend.dto.UnitDTO;
import com.enclave.backend.entity.Unit;

import java.util.List;

public interface UnitService {

    Unit findById(short id);

    Unit createUnit(UnitDTO unitDTO);

    List<Unit> getAllUnits();
}
