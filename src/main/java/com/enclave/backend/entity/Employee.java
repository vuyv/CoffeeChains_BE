package com.enclave.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import java.util.Set;

@Data
@Entity
@Table(name = "employee")
public class Employee extends AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private short id;

    @Column(length = 20)
    private String phone;

    @Column(length = 20)
    private String username;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
//    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
//    @JoinColumn(name = "role_id")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private Set<Order> orderCreate;

    @JsonIgnore
    @OneToMany(mappedBy = "canceledBy")
    private Set<Order> orderCancel;

    public enum Status{
        ACTIVE, INACTIVE
    }

    @Column(length = 30)
    private String email;
}
