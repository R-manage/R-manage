package com.rmanage.rmanage.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Entity
@Getter
public class CustomerService extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String email;
    private String content;
    private boolean isAnswered;

    @Builder
    public CustomerService(User user, String email, String content) {
        this.user = user;
        this.email = email;
        this.content = content;
        this.isAnswered = false;
    }
}
