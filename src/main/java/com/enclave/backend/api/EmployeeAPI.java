package com.enclave.backend.api;

import com.enclave.backend.dto.EmployeeDTO;
import com.enclave.backend.entity.Employee;
import com.enclave.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeAPI {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/new")
    public Employee createEmployee(@RequestBody EmployeeDTO dto) {
        return employeeService.createEmployee(dto);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable("id") short id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @GetMapping("/all")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}/all")
    public List getEmployeesByBranch() {
        return employeeService.getEmployeesByBranch();
    }
}
