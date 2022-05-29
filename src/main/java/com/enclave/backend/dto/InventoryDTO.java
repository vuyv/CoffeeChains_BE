package com.enclave.backend.dto;

import lombok.Data;

@Data
public class InventoryDTO {
    private short materialId;
    private double quantity;
    private short unitId;
}
