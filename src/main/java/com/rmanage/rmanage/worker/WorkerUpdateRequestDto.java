package com.rmanage.rmanage.worker;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WorkerUpdateRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime period;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime payDay;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime workAuthDate;

    private String workPlaceName;
    private String color;
    private String breakTime;
    private String holidayPay;
    private boolean isManager;
    private int hourlyWage;
    private String tax;
    private String wageType;

}
