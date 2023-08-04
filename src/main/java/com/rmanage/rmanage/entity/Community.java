package com.rmanage.rmanage.entity;

import com.rmanage.rmanage.workPlace.WorkPlace;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity

public class Community extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workplace_id")
    private WorkPlace workPlace;

    private String type;
    private String title;
    private String content;
    private String writer;
    private boolean isAnonymous;
    private int recommend;

    public Community(User user, WorkPlace workPlace, String type, String title, String content, String writer, boolean isAnonymous, int recommend) {
        this.user = user;
        this.workPlace = workPlace;
        this.type = type;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.isAnonymous = isAnonymous;
        this.recommend = recommend;
    }
}
