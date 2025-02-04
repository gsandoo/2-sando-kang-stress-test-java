package com.ktb.stresstest.dto.auth.res;

import com.ktb.stresstest.domain.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)

public record UserResDto(
        String email,
        String nickname,
        String profile
) {
    public static UserResDto create(User user){
        return UserResDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profile(user.getProfileUrl())
                .build();
    }
}
