package com.rmanage.rmanage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Getter
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workplace_id")
    private WorkPlace workPlace;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workerId")
    private Worker worker;
    private String type;
    private LocalDate expireDate;
    private String imageUrl;

    public Document(User user, WorkPlace workPlace, String type, LocalDate expireDate, Worker worker, String imageUrl) {
        this.user = user;
        this.workPlace = workPlace;
        this.type = type;
        this.expireDate = expireDate;
        this.worker = worker;
        this.imageUrl = imageUrl;
    }

    public Document(Long documentId, User user, WorkPlace workPlace, Worker worker, String type, String imageUrl, LocalDate expireDate) {
        this.documentId = documentId;
        this.user = user;
        this.workPlace = workPlace;
        this.worker = worker;
        this.type = type;
        this.imageUrl = imageUrl;
        this.expireDate = expireDate;
    }
}
