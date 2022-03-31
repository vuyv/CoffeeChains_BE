package com.enclave.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDTO {
    private short id;
    private String phone;
    private short branchId;
    private short roleId;
    private String name;
    private String username;
//    private String password;
    private String gender;
    private String address;
    private String avatar;
    private Date birth;
}
