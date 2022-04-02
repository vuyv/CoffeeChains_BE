package com.enclave.backend.dto;

import com.enclave.backend.entity.Branch;
import lombok.Data;

@Data
public class BranchDTO {
    private short id;
    private String name;
    private String address;
    private Branch.Status status;
}
