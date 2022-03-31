package com.enclave.backend.service.impl;

import com.enclave.backend.entity.Role;
import com.enclave.backend.repository.RoleRepository;
import com.enclave.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
