package com.enclave.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
@Table(schema = "email_sending")
public class EmailSending {

    @Id
    @Email
    @Column
    private String email;

    @Column
    private LocalTime timeSent;

    @Column
    private Date lastSent;

}
