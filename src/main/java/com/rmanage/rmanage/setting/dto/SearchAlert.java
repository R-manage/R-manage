package com.rmanage.rmanage.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchAlert {

    private long userId;
    private boolean alarm;
    private boolean preview;
    private boolean pushAlarm;
    private boolean pushSound;
    private boolean pushVibration;
}
