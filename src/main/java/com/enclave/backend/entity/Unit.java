package com.enclave.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "unit")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private short id;

    @Column
    private String unit;

    @Column
    private double netWeight;

    @Column
    private double rate;

    @ManyToMany(mappedBy = "units")
    @JsonIgnore
    private Collection<Material> materials;


//    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Collection<UnitMaterial> unitMaterials = new ArrayList<>();
}
