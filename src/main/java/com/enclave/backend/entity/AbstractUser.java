package com.enclave.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class AbstractUser {

    @Column(length = 10)
    private String name;

    @Column(length = 60)
    @JsonIgnore
    private String password;

    @Column(length = 20)
    private String gender;

    @Column(length = 120)
    private String address;

    @Column
    private String avatar;

    @Column
    private Date birth;
}
