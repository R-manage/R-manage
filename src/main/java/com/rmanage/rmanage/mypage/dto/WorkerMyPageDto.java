package com.rmanage.rmanage.mypage.dto;

import com.rmanage.rmanage.WorkAllowance.DateStat;
import com.rmanage.rmanage.WorkAllowance.HolidayStat;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.entity.Worker;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class WorkerMyPageDto {
    private String image;
    private String nickname;
    private List<WorkPlace> workPlaces;
    private long totalWorkTime;
    private long totalPay;
    private long currentMonthWorkTime;
    //thisDataStat.worktime이 이번달 총 일한시간
    private long currentMonthTotalPay;
    //datestat이용해서 시급 계산
    @Builder
    public WorkerMyPageDto(String image, String nickname, List<WorkPlace> workPlaces, long totalWorkTime, long totalPay, long currentMonthWorkTime, long currentMonthTotalPay) {
        this.image = image;
        this.nickname = nickname;
        this.workPlaces = workPlaces;
        this.totalWorkTime = totalWorkTime;
        this.totalPay = totalPay;
        this.currentMonthWorkTime = currentMonthWorkTime;
        this.currentMonthTotalPay = currentMonthTotalPay;
        // 이건 sum하는 방법 this.totalHolidayPay = holidayStats.stream().mapToLong(HolidayStat::getPay).sum();
    }
}

