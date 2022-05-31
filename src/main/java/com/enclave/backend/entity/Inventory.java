package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "inventory")
public class Inventory {

    @EmbeddedId
    private InventoryId inventoryId = new InventoryId();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "materialId")
    @MapsId("materialId")
    private Material rawMaterial;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "branchId")
    @MapsId("branchId")
    private Branch branch;

    @Column(length = 30)
    private double quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unitId")
    @MapsId("unitId")
    private Unit unit;

    @Column
    private Date createdAt;
}
