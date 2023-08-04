package com.rmanage.rmanage.setting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class PhoneResultDto {

    @JsonProperty("isPhone")
    private boolean isPhone;

    private String phonenumber;

    private String phoneAuthDate;
}
