package com.enclave.backend.entity;

import lombok.Data;

@Data
public class Password {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
