package com.enclave.backend.api;

import com.enclave.backend.dto.EmployeeDTO;
import com.enclave.backend.dto.PasswordDTO;
import com.enclave.backend.dto.PasswordResetDTO;
import com.enclave.backend.dto.employee.BranchEmployeeDTO;
import com.enclave.backend.entity.Employee;
import com.enclave.backend.service.EmployeeService;
import com.enclave.backend.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeAPI {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OTPService otpService;

    @PostMapping("/new")
    public ResponseEntity <Employee> createEmployee(@RequestBody EmployeeDTO dto) {
        try{
            return new ResponseEntity<>(employeeService.createEmployee(dto), HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't create employee", e);
        }
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

    @GetMapping("/countAll")
    public Long getCountOfAllEmployee(){
        return employeeService.getCountOfAllEmployee();
    }

    //manager
    @GetMapping("/countOfBranch")
    public int getCountOfBranchEmployee(){
        return employeeService.getCountOfBranchEmployee();
    }

    @GetMapping("/find/{phone}")
    public ResponseEntity <Employee> getEmployeeByPhone(@PathVariable("phone") String phone) {
        try{
            return new ResponseEntity<>(employeeService.getEmployeeByPhone(phone), HttpStatus.OK) ;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number doesn't exist.", e);
        }
    }

    @PutMapping("reset_password/{phone}")
    public ResponseEntity<Employee> resetPassword(@PathVariable("phone") String phone, @RequestBody PasswordResetDTO passwordResetDTO){
        try{
            return new ResponseEntity<>(employeeService.resetPassword(phone, passwordResetDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't reset password", e);
        }
    }

//    @GetMapping("/send_otp/{phone}")
//    public void sendOtp(@PathVariable("phone") String phone) throws FirebaseAuthException, ExecutionException, InterruptedException {
//        otpService.sendOtp(phone);
//    }
}
