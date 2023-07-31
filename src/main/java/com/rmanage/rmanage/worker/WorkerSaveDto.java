package com.rmanage.rmanage.worker;

import com.rmanage.rmanage.entity.User;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class WorkerSaveDto {

    private Long userId;
    private String name;
    private LocalDateTime period;
    private String color;
    //private String breakTime;
    private boolean holidayPay;
    private int hourlyWage;
    private LocalDateTime payDay;


    public Worker toEntity(User user) {
        return Worker.builder()
                .user(user)
                .name(name)
                .period(period)
                .color(color)
                .isCertified(false)
                //.breakTime(breakTime)
                .holidayPay(holidayPay)
                .hourlyWage(hourlyWage)
                .payDay(payDay)
                .build();

    }
}
