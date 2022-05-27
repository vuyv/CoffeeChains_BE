package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class RecipeId implements Serializable {
    @Column(name = "productId")
    private short productId;

    @Column(name = "materialId")
    private short materialId;
}
