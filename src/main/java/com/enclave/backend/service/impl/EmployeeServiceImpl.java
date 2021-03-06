package com.enclave.backend.service.impl;

import com.enclave.backend.converter.EmployeeConverter;
import com.enclave.backend.converter.PasswordConverter;
import com.enclave.backend.converter.PasswordResetConverter;
import com.enclave.backend.dto.EmployeeDTO;
import com.enclave.backend.dto.PasswordDTO;
import com.enclave.backend.dto.PasswordResetDTO;
import com.enclave.backend.dto.employee.BranchEmployeeDTO;
import com.enclave.backend.entity.*;
import com.enclave.backend.jwt.CustomUserDetails;
import com.enclave.backend.repository.BranchRepository;
import com.enclave.backend.repository.EmployeeRepository;
import com.enclave.backend.repository.RoleRepository;
import com.enclave.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private EmployeeConverter employeeConverter;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Autowired
    private PasswordConverter passwordConverter;

    @Autowired
    private PasswordResetConverter passwordResetConverter;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String currentUserName() {
        return getAuthentication().getName();
    }

    @Override
    public Employee getCurrentEmployee() {
//        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = currentUserName();
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Branch getBranchOfCurrentEmployee(){
        return getCurrentEmployee().getBranch();
    }

    @Override
    public Employee changePassword(short id, PasswordDTO passwordDTO) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        Password password = passwordConverter.toEntity(passwordDTO);
        boolean value = BCrypt.checkpw(password.getOldPassword(), employee.getPassword());
        if (value) {
            if (password.getNewPassword().equals(password.getConfirmPassword())) {
                employee.setPassword(passwordEncode.encode(password.getConfirmPassword()));
            }
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Long getCountOfAllEmployee() {
        return employeeRepository.count();
    }

    @Override
    public int getCountOfBranchEmployee() {
        Employee manager = getCurrentEmployee();
        short branchId = manager.getBranch().getId();

        return employeeRepository.getCountOfBranchEmployee(branchId);
    }

    @Override
    public int getCountEmployeeEachBranch(short branchId) {
        return employeeRepository.getCountOfBranchEmployee(branchId);
    }

    @Override
    public List<Object[]> getCountOfEmployeeEachBranch() {
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            queryResult = employeeRepository.getCountOfEmployeeEachBranch();
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;

    }

    @Override
    public Employee createEmployee(EmployeeDTO dto) {
        short branchId = dto.getBranchId();
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new IllegalArgumentException("Invalid branch Id:" + branchId));

        short roleId = dto.getRoleId();
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + roleId));

        Employee newEmployee = employeeConverter.toEntity(dto);

        newEmployee.setRole(role);
        newEmployee.setBranch(branch);
        newEmployee.setPassword(passwordEncode.encode("123123"));
        newEmployee.setStatus(Employee.Status.ACTIVE);
//        employeeRepository.save(newEmployee);
        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(short id, EmployeeDTO employeeDTO) {

        Employee oldEmployee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        Branch branch = branchRepository.findById(employeeDTO.getBranchId()).orElseThrow(() -> new IllegalArgumentException("Invalid branch Id:" + employeeDTO.getBranchId()));
        Role role = roleRepository.findById(employeeDTO.getRoleId()).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + employeeDTO.getRoleId()));

        oldEmployee.setName(employeeDTO.getName());
        oldEmployee.setPhone(employeeDTO.getPhone());
        oldEmployee.setBirth(employeeDTO.getBirth());
        oldEmployee.setAvatar(employeeDTO.getAvatar());
        oldEmployee.setGender(employeeDTO.getGender());
        oldEmployee.setAddress(employeeDTO.getAddress());
        oldEmployee.setUsername(employeeDTO.getUsername());
        oldEmployee.setBranch(branch);
        oldEmployee.setRole(role);

        return employeeRepository.save(oldEmployee);
    }

    @Override
    public Employee getEmployeeById(short id) {
        return employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByBranch() {
        short branchId = getCurrentEmployee().getBranch().getId();
        return employeeRepository.findByBranch(branchId);
    }

    @Override
    public Employee disableEmployee(short id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employee.setStatus(Employee.Status.INACTIVE);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee createEmployeeInBranch(BranchEmployeeDTO branchEmployeeDTO) {
        Employee newEmployee = employeeConverter.toEntity(branchEmployeeDTO);

        Employee manager = getCurrentEmployee();
        newEmployee.setBranch(manager.getBranch());

        Role role = roleRepository.findById((short) 3).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:"));
        newEmployee.setRole(role);

        newEmployee.setPassword(passwordEncode.encode("123123"));
        newEmployee.setStatus(Employee.Status.ACTIVE);
//        employeeRepository.save(newEmployee);
        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee getEmployeeByPhone(String phone) {
        return employeeRepository.findByPhone(phone).orElseThrow(() -> new IllegalArgumentException("Invalid phone number:" + phone));
    }

    @Override
    public Employee resetPassword(String phone, PasswordResetDTO passwordResetDTO) {
        Employee employee = employeeRepository.findByPhone(phone).orElseThrow(() -> new IllegalArgumentException("Invalid phone number:" + phone));
        PasswordReset password = passwordResetConverter.toEntity(passwordResetDTO);
        if (password.getPassword().equals(password.getConfirmPassword())) {
            employee.setPassword(passwordEncode.encode(password.getConfirmPassword()));
            employeeRepository.save(employee);
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid email:" + email));
    }

    @Override
    public Employee getEmployeeHasRoleOwner(){
        List<Employee> allEmployees = employeeRepository.findAll();
        return allEmployees.stream().filter(employee -> employee.getRole().getName().equals("OWNER")).findFirst().get();
    }

    @Override
    public List<Employee> getEmployeeHasRoleManager(){
        List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> managers = new ArrayList<>();
        allEmployees.forEach(employee -> {
            if ("MANAGER".equals(employee.getRole().getName())){
                managers.add(employee);
            }
        });
        return managers;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee user = employeeRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new CustomUserDetails(user);
    }


    @Transactional
    public UserDetails loadUserById(Short id) {
        Optional<Employee> optionalUser = employeeRepository.findById(id);
        Employee user = optionalUser.get();
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + id);
        }
        return new CustomUserDetails(user);
    }

}
