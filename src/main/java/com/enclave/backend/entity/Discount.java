package com.enclave.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;


@Data
@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @Column
    private String code;

    @Column
    private String title;

    @Column
    private double percent;

    @Column
    private Date startedAt;

    @Column
    private Date endedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "discount")
    private Set<Order> orders;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        UPCOMING, HAPPENING, EXPIRED
    }
}
