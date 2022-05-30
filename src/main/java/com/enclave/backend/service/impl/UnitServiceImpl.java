package com.enclave.backend.service.impl;

import com.enclave.backend.entity.Unit;
import com.enclave.backend.repository.UnitRepository;
import com.enclave.backend.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Override
    public Unit findById(short id) {
        return unitRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid unit id: " + id));
    }
}
