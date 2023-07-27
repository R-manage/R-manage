package com.rmanage.rmanage.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Entity

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

    public CustomerService(String email, String content, boolean isAnswered) {
        this.email = email;
        this.content = content;
        this.isAnswered = isAnswered;
    }
}
