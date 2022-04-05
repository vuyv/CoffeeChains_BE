package com.enclave.backend.converter;

import com.enclave.backend.dto.PasswordDTO;
import com.enclave.backend.entity.Password;
import org.springframework.stereotype.Component;

@Component
public class PasswordConverter {
    public Password toEntity(PasswordDTO passwordDTO){
        Password entity = new Password();
        entity.setOldPassword(passwordDTO.getOldPassword());
        entity.setNewPassword(passwordDTO.getNewPassword());
        entity.setConfirmPassword(passwordDTO.getConfirmPassword());
        return entity;
    }
}
