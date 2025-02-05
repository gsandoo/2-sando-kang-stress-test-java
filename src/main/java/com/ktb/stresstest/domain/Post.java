package com.ktb.stresstest.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String url;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "comments")
    private Long comments;

    @Column(name = "views")
    private Long views;

    @Builder
    public Post(User user, String title, String content, String url) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.url = url;
        this.date = LocalDate.now();
        this.likes = Long.valueOf(0);
        this.comments = Long.valueOf(0);
        this.views = Long.valueOf(0);
    }

    public void updateTitle(String title) { this.title = title; }
    public void updateContent(String content) { this.title = content; }
    public void updateUrl(String url) { this.url = url; }
    public void increaseLikes(){
        this.likes+=1;
    }
    public void decreaseLikes(){ this.likes-=1; }
    public void increaseViews(){ this.views+=1; }
    public void increaseComments(){
        this.comments+=1;
    }
}
