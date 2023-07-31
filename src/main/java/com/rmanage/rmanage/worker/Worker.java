package com.rmanage.rmanage.worker;

import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
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
    private boolean holidayPay;
    private int hourlyWage;
    private LocalDateTime payDay;


    @Builder
    public Worker(WorkPlace workPlace, User user, String name, LocalDateTime period, String color, boolean isCertified, boolean holidayPay, int hourlyWage, LocalDateTime payDay) {
        this.workPlace = workPlace;
        this.user = user;
        this.name = name;
        this.period = period;
        this.color = color;
        this.isCertified = isCertified;
        this.holidayPay = holidayPay;
        this.hourlyWage = hourlyWage;
        this.payDay = payDay;
    }
}
