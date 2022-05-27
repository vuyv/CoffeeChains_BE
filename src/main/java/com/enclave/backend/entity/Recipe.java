package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "recipe")
public class Recipe {
    @EmbeddedId
    private RecipeId recipeId = new RecipeId();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    @MapsId("productId")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "materialId")
    @MapsId("materialId")
    private Material material;

    @Column
    private double amount;
}
