package com.rmanage.rmanage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Entity
@Getter

public class WorkPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workPlaceId;

    private String name;
    private String adminCode;



    @Builder
    public WorkPlace(String name, String adminCode) {

        this.name = name;
        this.adminCode = adminCode;

    }

    public void update(String name) {
        this.name = name;
    }
}
