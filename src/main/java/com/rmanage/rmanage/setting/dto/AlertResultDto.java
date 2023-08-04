package com.rmanage.rmanage.setting.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlertResultDto {
    private boolean alarm;
    private boolean preview;
    private boolean pushAlarm;
    private boolean pushSound;
    private boolean pushVibration;
}
