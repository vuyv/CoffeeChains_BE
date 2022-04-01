package com.enclave.backend.converter;

import com.enclave.backend.dto.EmployeeDTO;
import com.enclave.backend.dto.employee.BranchEmployeeDTO;
import com.enclave.backend.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

    public Employee toEntity(EmployeeDTO dto){
        Employee entity = new Employee();
        entity.setUsername(dto.getUsername());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setName(dto.getName());
        entity.setAvatar(dto.getAvatar());
        entity.setGender(dto.getGender());
        entity.setBirth(dto.getBirth());
        return entity;
    }

    public Employee toEntity(BranchEmployeeDTO dto){
        Employee entity = new Employee();
        entity.setUsername(dto.getUsername());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setName(dto.getName());
        entity.setAvatar(dto.getAvatar());
        entity.setGender(dto.getGender());
        entity.setBirth(dto.getBirth());
        return entity;
    }
}
