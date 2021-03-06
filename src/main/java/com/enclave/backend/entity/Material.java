package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private short id;

    @Column(length = 30)
    private String name;

    @Column
    private String image;

    @ManyToMany
    @JoinTable(name = "unit_material",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id"))
    private Collection<Unit> units;

//    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Collection<UnitMaterial> unitMaterials = new ArrayList<>();
}
