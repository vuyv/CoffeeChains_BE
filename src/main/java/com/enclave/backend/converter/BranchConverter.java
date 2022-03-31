package com.enclave.backend.converter;

import com.enclave.backend.dto.BranchDTO;
import com.enclave.backend.entity.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchConverter {
    public Branch toEntity(BranchDTO dto) {
        Branch entity = new Branch();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(entity.getAddress());
        entity.setStatus(Branch.Status.ACTIVE);
        return entity;
    }
}
