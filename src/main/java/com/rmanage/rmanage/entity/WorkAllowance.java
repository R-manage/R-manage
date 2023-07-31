package com.rmanage.rmanage.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity

public class WorkAllowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workplaceId")
    private WorkPlace workPlace;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workerId")
    private Worker worker;
    private LocalDate workDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public WorkAllowance(User user, WorkPlace workPlace, LocalDate workDate, LocalDateTime startTime, LocalDateTime endTime) {
        this.user = user;
        this.workPlace = workPlace;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
