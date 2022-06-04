package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
public class DailyInventoryId implements Serializable  {

    @Column(name = "materialId")
    private short materialId;

    @Column(name = "branchId")
    private short branchId;

    @Column(name = "unitId")
    private short unitId;

    @Column(name="updatedAt")
    private Date updatedAt = new Date();
}
