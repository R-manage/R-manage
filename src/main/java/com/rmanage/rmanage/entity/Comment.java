package com.rmanage.rmanage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@Entity
@Getter

public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Community community;


    private String content;

    //@ColumnDefault("false")
    private boolean isAnonymous;

    public Comment(User user, Community community, String content, boolean isAnonymous) {
        this.user = user;
        this.community = community;
        this.content = content;
        this.isAnonymous = isAnonymous;
    }

}
