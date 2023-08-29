package com.rmanage.rmanage.settingAdmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class SettingAdminResultDto {
    private long workerId;
    private String name;
    @JsonProperty("isManager")
    private boolean isManager;
}
