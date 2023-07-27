package com.rmanage.rmanage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Entity

public class WorkPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workPlaceId;

    private String name;
    private String wageType;
    private boolean tax;
    private String authenticationCode;

    public WorkPlace(String name, String wageType, boolean tax, String authenticationCode) {
        this.name = name;
        this.wageType = wageType;
        this.tax = tax;
        this.authenticationCode = authenticationCode;
    }
}
