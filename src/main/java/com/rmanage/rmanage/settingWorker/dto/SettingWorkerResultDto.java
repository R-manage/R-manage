package com.rmanage.rmanage.settingWorker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class SettingWorkerResultDto {
    private long workplaceId;
    private String name;
    private String validity;
}
