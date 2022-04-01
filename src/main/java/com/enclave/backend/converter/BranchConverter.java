package com.enclave.backend.converter;

import com.enclave.backend.dto.BranchDTO;
import com.enclave.backend.entity.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchConverter {
    public Branch toEntity(BranchDTO dto) {
        Branch entity = new Branch();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setStatus(Branch.Status.ACTIVE);
        return entity;
    }
}
