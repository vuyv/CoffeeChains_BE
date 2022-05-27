package com.enclave.backend.service;

import com.enclave.backend.dto.EmployeeDTO;
import com.enclave.backend.dto.PasswordDTO;
import com.enclave.backend.dto.PasswordResetDTO;
import com.enclave.backend.dto.employee.BranchEmployeeDTO;
import com.enclave.backend.entity.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    Employee createEmployee(EmployeeDTO dto);

    Employee updateEmployee(short id, EmployeeDTO employeeDTO);

    Employee getEmployeeById(short id);

    List<Employee> getEmployees();

    List<Employee> getEmployeesByBranch();

    Employee disableEmployee(short id);

    Employee createEmployeeInBranch(BranchEmployeeDTO branchEmployeeDTO);

    Employee getCurrentEmployee();

    Employee changePassword(short id, PasswordDTO passwordDTO);

    Long getCountOfAllEmployee();

    int getCountOfBranchEmployee();
    int getCountEmployeeEachBranch(short branchId);

    List<Object[]> getCountOfEmployeeEachBranch();

    Employee getEmployeeByPhone(String phone);

    Employee resetPassword(String phone, PasswordResetDTO passwordResetDTO);

    Employee getEmployeeByEmail(String email);

    List<Employee> getEmployeeHasRoleManager();

    Employee getEmployeeHasRoleOwner();
}
