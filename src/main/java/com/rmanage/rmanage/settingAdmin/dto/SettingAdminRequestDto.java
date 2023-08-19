package com.rmanage.rmanage.settingAdmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
public class SettingAdminRequestDto {
    private long workerId;

    @JsonProperty("isManager")
    private boolean isManager;
}
