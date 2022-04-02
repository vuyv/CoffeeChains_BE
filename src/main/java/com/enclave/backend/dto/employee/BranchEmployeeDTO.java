package com.enclave.backend.dto.employee;

import lombok.Data;

import java.util.Date;

@Data
public class BranchEmployeeDTO {
    private String phone;
    private String name;
    private String username;
    private String gender;
    private String address;
    private String avatar;
    private Date birth;
}
