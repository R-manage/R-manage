package com.rmanage.rmanage.mypage.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMyPageDto {
    private String image;
    private String nickname;
    private int employeeNum;
    private long totalPay;
    private long currentMonthTotalPay;
    @Builder
    public AdminMyPageDto(String image, String nickname, int employeeNum, long totalPay, long currentMonthTotalPay) {
        this.image = image;
        this.nickname = nickname;
        this.employeeNum = employeeNum;
        this.totalPay = totalPay;
        this.currentMonthTotalPay = currentMonthTotalPay;
    }
}
