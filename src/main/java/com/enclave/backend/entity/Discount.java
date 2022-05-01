package com.enclave.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import static com.enclave.backend.entity.DateUtil.endOfDay;
import static com.enclave.backend.entity.DateUtil.startOfDay;

@Data
@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @Column(length = 6)
    private String code;

    @Column(length = 150)
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

    //    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    public boolean isHappening() {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(startOfDay(this.startedAt)) && currentDate.isBefore(endOfDay(this.endedAt));
    }

    @JsonIgnore
    public boolean isExpired() {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(endOfDay(this.endedAt));
    }

    @JsonIgnore
    public boolean isUpcoming() {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isBefore(startOfDay(this.startedAt));
    }

    public Status getStatus() {
        if (isUpcoming()) {
            return Status.UPCOMING;
        }
        if (isExpired()) {
            return Status.EXPIRED;
        }
        return Status.HAPPENING;
    }

    public Status getStatusOnDate(LocalDateTime date) {
        if (date.isBefore(startOfDay(this.startedAt))) {
            return Status.UPCOMING;
        }
        if (date.isAfter(endOfDay(this.endedAt)) ){
            return Status.EXPIRED;
        }
        return Status.HAPPENING;
    }

    public enum Status {
        UPCOMING, HAPPENING, EXPIRED
    }
}
