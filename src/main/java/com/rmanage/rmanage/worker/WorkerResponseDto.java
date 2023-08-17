package com.rmanage.rmanage.worker;

import com.rmanage.rmanage.entity.Worker;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
public class WorkerResponseDto {
    private Long workerId;

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
    private

    @Builder
    public WorkerResponseDto(Worker worker) {
        this.workerId = worker.getWorkerId();
        this.name = worker.getName();
        this.workPlaceName = worker.getWorkPlace().getName();
        this.period = worker.getPeriod();
        this.color = worker.getColor();
        this.isCertified = worker.isCertified();
        this.breakTime = worker.getBreakTime();
        this.holidayPay = worker.getHolidayPay();
        this.isManager = worker.isManager();
        this.hourlyWage = worker.getHourlyWage();
        this.payDay = worker.getPayDay();
        this.tax = worker.getTax();
        this.wageType = worker.getWageType();
        this.workAuthDate = worker.getWorkAuthDate();

    }
}
