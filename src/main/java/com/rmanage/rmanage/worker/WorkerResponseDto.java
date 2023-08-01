package com.rmanage.rmanage.worker;

import lombok.Builder;

import java.time.LocalDateTime;

public class WorkerResponseDto {
    private Long workerId;

    private String name;
    private LocalDateTime period;
    private String color;
    private boolean isCertified;
    private String breakTime;
    private String holidayPay;
    private int hourlyWage;
    private LocalDateTime payDay;

    @Builder
    public WorkerResponseDto(Worker worker) {
        this.workerId = worker.getWorkerId();
        this.name = worker.getName();
        this.period = worker.getPeriod();
        this.color = worker.getColor();
        this.isCertified = worker.isCertified();
        this.breakTime = worker.getBreakTime();
        this.holidayPay = worker.getHolidayPay();
        this.hourlyWage = worker.getHourlyWage();
        this.payDay = worker.getPayDay();
    }
}
