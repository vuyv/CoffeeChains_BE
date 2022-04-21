package com.enclave.backend.api;

import com.enclave.backend.dto.EmployeeDTO;
import com.enclave.backend.dto.PasswordDTO;
import com.enclave.backend.dto.employee.BranchEmployeeDTO;
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
    public Employee updateEmployee(@PathVariable("id") short id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO );
    }

    @GetMapping("/all")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/branch/all")
    public List getEmployeesByBranch() {
        return employeeService.getEmployeesByBranch();
    }

    @PutMapping("/disable/{id}")
    public Employee disableEmployee(@PathVariable("id") short id){
        return employeeService.disableEmployee(id);
    }

    @PostMapping("/newInBranch")
    public Employee createEmployeeInBranch(@RequestBody BranchEmployeeDTO branchEmployeeDTO){
    return employeeService.createEmployeeInBranch(branchEmployeeDTO);
    }

    @GetMapping("/currentUser")
    public Employee getCurrentEmployee(){
        return employeeService.getCurrentEmployee();
    }

    @PutMapping("/changePassword/{id}")
    public Employee changePassword(@PathVariable("id") short id, @RequestBody PasswordDTO passwordDTO){
        return employeeService.changePassword(id, passwordDTO);
    }



    //manager
    @GetMapping("/countOfBranch")
    public int getCountOfBranchEmployee(){
        return employeeService.getCountOfBranchEmployee();
    }

    //owner
    @GetMapping("/countAll")
    public Long getCountOfAllEmployee(){
        return employeeService.getCountOfAllEmployee();
    }

    @GetMapping("/count/eachBranch")
    public List<Object[]> getCountOfEmployeeEachBranch(){
        return employeeService.getCountOfEmployeeEachBranch();
    }
}
