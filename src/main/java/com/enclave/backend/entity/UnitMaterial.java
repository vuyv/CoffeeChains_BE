package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="unit_material")
public class UnitMaterial {
    @EmbeddedId
    private UnitMaterialId unitMaterialId = new UnitMaterialId();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "materialId")
    @MapsId("materialId")
    private Material material;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unitId")
    @MapsId("unitId")
    private UnitConverter unitConverter;
}
