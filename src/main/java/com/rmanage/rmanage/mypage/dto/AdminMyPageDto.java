package com.rmanage.rmanage.mypage.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMyPageDto {
    //사진은 어떻게 구현해야할지?
    private String nickname;
    private int employee_num;
    private long sum_payment;
    //근로자 지급금액이 근로자별로 얼마주는건지 나오는건지, 그러면 근로자별로 이름도 나오는건지
    private long sum_this_month_payment;
}
