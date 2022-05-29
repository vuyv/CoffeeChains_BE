package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class UnitMaterialId implements Serializable {
    @Column(name = "materialId")
    private short materialId;

    @Column(name = "unitId")
    private short unitId;
}
