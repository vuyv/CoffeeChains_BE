package com.enclave.backend.dto;

import lombok.Data;

@Data
public class RecipeResponseDTO {
    private short materialId;
    private String materialName;
    private int amount;
}
