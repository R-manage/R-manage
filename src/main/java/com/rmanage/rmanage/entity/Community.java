package com.rmanage.rmanage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter

public class Community extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId = null;

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
    //@ColumnDefault("false")
    private boolean isAnonymous;
    private int recommend;
   //@ColumnDefault("")
    private String imageUrl;

    // 처음 글쓸 때 생성되어야 될듯
    public Community(User user, WorkPlace workPlace, String type, String title, String content, String writer, boolean isAnonymous, int recommend, String imageUrl) {
        this.user = user;
        this.workPlace = workPlace;
        this.type = type;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.isAnonymous = isAnonymous;
        this.recommend = recommend;
        this.imageUrl = imageUrl;

    }

    public void updateCommunity(String title, String content, String imageUrl){
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

}
