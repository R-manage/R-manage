package com.rmanage.rmanage.entity;

import com.rmanage.rmanage.workPlace.WorkPlace;
import com.rmanage.rmanage.worker.Worker;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Entity

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

    public Document(User user, WorkPlace workPlace, String type, LocalDate expireDate) {
        this.user = user;
        this.workPlace = workPlace;
        this.type = type;
        this.expireDate = expireDate;
    }
}
