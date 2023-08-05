package com.rmanage.rmanage.worker;

import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.Worker;
import com.rmanage.rmanage.entity.WorkPlace;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class WorkerSaveDto {

    private Long userId;
    private String name;
    private String workPlaceName;
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


    public Worker toEntity(User user, WorkPlace workPlace) {
        return Worker.builder()
                .user(user)
                .workPlace(workPlace)
                .name(name)
                .period(period)
                .color(color)
                .isCertified(false)
                .breakTime(breakTime)
                .holidayPay(holidayPay)
                .isManager(false)
                .hourlyWage(hourlyWage)
                .payDay(payDay)
                .tax(tax)
                .wageType(wageType)
                .workAuthDate(workAuthDate)
                .build();

    }
}
