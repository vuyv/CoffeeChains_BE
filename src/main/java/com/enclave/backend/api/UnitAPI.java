package com.enclave.backend.api;

import com.enclave.backend.entity.Unit;
import com.enclave.backend.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/unit")
public class UnitAPI {
    @Autowired
    private UnitService unitService;

    @GetMapping("/all")
    public List<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }
}
