package com.rmanage.rmanage.entity;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor

public class WorkerId {
    private  Long workPlaceId;
    private  Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerId that = (WorkerId) o;
        return Objects.equals(workPlaceId, that.workPlaceId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workPlaceId, userId);
    }

    public WorkerId(Long workPlaceId, Long userId) {
        this.workPlaceId = workPlaceId;
        this.userId = userId;
    }
}
