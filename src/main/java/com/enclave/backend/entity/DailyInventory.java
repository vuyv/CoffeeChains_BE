package com.enclave.backend.entity;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "daily_inventory")
public class DailyInventory {

    @EmbeddedId
    private DailyInventoryId dailyInventoryId = new DailyInventoryId();

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

//    @MapsId("updatedAt")
//    @Column
//    @NaturalId
//    private Date updatedAt;
}
