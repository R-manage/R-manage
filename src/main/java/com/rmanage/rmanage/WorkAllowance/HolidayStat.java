package com.rmanage.rmanage.WorkAllowance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class HolidayStat {

    private LocalDate start;
    private LocalDate end;
    private long pay;

}
