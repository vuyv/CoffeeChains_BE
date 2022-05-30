package com.enclave.backend.dto;

import lombok.Data;

import java.util.List;
@Data
public class MaterialDTO {
    private String name;
    private List<UnitDTO> units;
}
