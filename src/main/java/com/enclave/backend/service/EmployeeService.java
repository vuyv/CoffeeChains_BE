package com.enclave.backend.service;

import com.enclave.backend.dto.EmployeeDTO;
import com.enclave.backend.entity.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    Employee createEmployee(EmployeeDTO dto);

    Employee updateEmployee(Employee employee);

    Employee getEmployeeById(short id);

    List<Employee> getEmployees();

    List<Employee> getEmployeesByBranch();
}
