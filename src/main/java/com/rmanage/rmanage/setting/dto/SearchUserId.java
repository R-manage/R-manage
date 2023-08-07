package com.rmanage.rmanage.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchUserId {
    private long userId;

    public long setUserId(long userId) {
        return this.userId = userId;
    }
}
