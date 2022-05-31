package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class InventoryId implements Serializable {
    @Column(name = "materialId")
    private short materialId;

    @Column(name = "branchId")
    private short branchId;

    @Column(name = "unitId")
    private short unitId;
}
