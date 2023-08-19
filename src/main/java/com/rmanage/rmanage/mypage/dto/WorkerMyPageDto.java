package com.rmanage.rmanage.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class WorkerMyPageDto {
    //사진은 어떻게 해야할지
    private String nickname;
    private String workplace;
    private int sum_worktime;
    private long sum_pay;
    private int sum_this_month_worktime;
    private int sum_this_month_pay;
}

