package com.ktb.stresstest.domain;

import com.ktb.stresstest.type.EUserRole;
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

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private EUserRole role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "profile")
    private String profileUrl;

    @Builder
    public User(Long id, String email, String password, String nickname, EUserRole role, String refreshToken, String profileUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.refreshToken = refreshToken;
        this.profileUrl = profileUrl;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUserNickname(String nickname) {
        if (nickname != null && !nickname.isEmpty()) {
            this.nickname = nickname;
        }
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileUrl = profileImageUrl;
    }

    public void deleteProfileImage() {
        this.profileUrl = null;
    }
}
