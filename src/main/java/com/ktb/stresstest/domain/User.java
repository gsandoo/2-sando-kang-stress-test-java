package com.ktb.stresstest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email" , nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile")
    private String profileUrl;

    @Builder
    public User(Long id, String email, String password, String nickname, String profileUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }
}
