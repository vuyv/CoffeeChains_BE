package com.enclave.backend.converter;

import com.enclave.backend.dto.PasswordResetDTO;
import com.enclave.backend.entity.PasswordReset;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetConverter {
    public PasswordReset toEntity(PasswordResetDTO passwordResetDTO){
        PasswordReset entity = new PasswordReset();
        entity.setPassword(passwordResetDTO.getPassword());
        entity.setConfirmPassword(passwordResetDTO.getConfirmPassword());
        return entity;
    }
}
