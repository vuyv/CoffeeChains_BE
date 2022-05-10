package com.enclave.backend.entity;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;

@Data
@Builder
@Jacksonized
public class DailyMailCreationRequest {
    @Email
    private final String email;
}
