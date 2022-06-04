package com.enclave.backend.dto;

import lombok.Data;

@Data
public class RecipeDTO {
    private short productId;
    private short materialId;
    private int amount;
}
