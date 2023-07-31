package com.rmanage.rmanage.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Entity

public class Worker {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;
    @MapsId("workplace_id")
    @ManyToOne
    private WorkPlace workPlace;

    @MapsId("user_id")
    @ManyToOne
    private User user;


    private String name;
    private Timestamp period;
    private String color;
    private boolean isCertified;
    private String breakTime;
    private boolean holidayPay;
    private boolean isManager;
    private int hourlyWage;

    public Worker(WorkPlace workPlace, User user, String name, Timestamp period, String color, boolean isCertified, String breakTime, boolean holidayPay, boolean isManager, int hourlyWage) {
        this.workPlace = workPlace;
        this.user = user;
        this.name = name;
        this.period = period;
        this.color = color;
        this.isCertified = isCertified;
        this.breakTime = breakTime;
        this.holidayPay = holidayPay;
        this.isManager = isManager;
        this.hourlyWage = hourlyWage;
    }
}
