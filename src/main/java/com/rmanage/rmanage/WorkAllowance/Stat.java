package com.rmanage.rmanage.WorkAllowance;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Stat {

    private long totalWorkTime;
    private long totalPay;
    private List<DateStat> dateStats;
    private List<HolidayStat> holidayStats;
    private long totalHolidayPay;

    @Builder
    public Stat(long totalWorkTime, long totalPay, List<DateStat> dateStats, List<HolidayStat> holidayStats) {
        this.totalWorkTime = totalWorkTime;
        this.totalPay = totalPay;
        this.dateStats = dateStats;
        this.holidayStats = holidayStats;
        this.totalHolidayPay = holidayStats.stream().mapToLong(HolidayStat::getPay).sum();
    }
}
