package com.rmanage.rmanage.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
public class Worker {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;

    @ManyToOne
    @JoinColumn(name = "workPlace_id")
    private WorkPlace workPlace;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private LocalDateTime period;
    private String color;
    private boolean isCertified;
    private String breakTime;
    private String holidayPay;
    private boolean isManager;
    private int hourlyWage;
    private LocalDateTime payDay;
    private String tax;
    private String wageType;
    private LocalDateTime workAuthDate;


    @Builder
    public Worker(WorkPlace workPlace, User user, String name, LocalDateTime period, String color, boolean isCertified, String breakTime, String holidayPay, boolean isManager, int hourlyWage, LocalDateTime payDay, String tax, String wageType, LocalDateTime workAuthDate) {
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
        this.payDay = payDay;
        this.tax = tax;
        this.wageType = wageType;
        this.workAuthDate = workAuthDate;

    }
}
