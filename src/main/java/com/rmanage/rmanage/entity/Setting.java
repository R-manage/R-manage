package com.rmanage.rmanage.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Setting {

    @Id
    private Long userId;
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private boolean alarm;
    private boolean preview;
    private boolean pushAlarm;
    private boolean pushSound;
    private boolean pushVibration;
    private String accountSecurity;


    @Builder
    public Setting(User user, boolean alarm, boolean preview, boolean pushAlarm, boolean pushSound, boolean pushVibration, String accountSecurity) {
        this.user = user;
        this.alarm = alarm;
        this.preview = preview;
        this.pushAlarm = pushAlarm;
        this.pushSound = pushSound;
        this.pushVibration = pushVibration;
        this.accountSecurity = accountSecurity;
    }

    public void update(boolean alarm, boolean preview, boolean pushAlarm, boolean pushSound, boolean pushVibration) {
        this.alarm = alarm;
        this.preview = preview;
        this.pushAlarm = pushAlarm;
        this.pushSound = pushSound;
        this.pushVibration = pushVibration;
    }
}
