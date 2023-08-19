package com.rmanage.rmanage.settingWorker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Data
public class SettingWorkerResponseDto {
    @JsonProperty("isSuccess")
    private boolean isSuccess;

    private int code;

    private String message;

    private List<SettingWorkerResultDto> result;
}
