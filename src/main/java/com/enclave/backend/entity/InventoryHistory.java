package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "inventory_history")
public class InventoryHistory {

    @EmbeddedId
    private InventoryHistoryId inventoryHistoryId = new InventoryHistoryId();

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
}
