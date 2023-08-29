package com.rmanage.rmanage.WorkAllowance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DateStat {

    private LocalDate date;
    private long workTime;
}
