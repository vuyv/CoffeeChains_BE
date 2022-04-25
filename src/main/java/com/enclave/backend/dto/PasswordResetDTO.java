package com.enclave.backend.dto;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String password;
    private String confirmPassword;
}
