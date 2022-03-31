package com.enclave.backend.api;

import com.enclave.backend.entity.Branch;
import com.enclave.backend.entity.Role;
import com.enclave.backend.service.RoleService;
import com.enclave.backend.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleAPI {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.getRoles();
    }
}
