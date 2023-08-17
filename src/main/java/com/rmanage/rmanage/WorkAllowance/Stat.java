package com.rmanage.rmanage.WorkAllowance;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Stat {

    private long totalWorkTime;
    private long totalPay;
    private List<DateStat> dateStats;
    private List<HolidayStat> holidayStats;
}
