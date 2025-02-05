package com.ktb.stresstest.dto.auth.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserInfoDto(
        UserResDto user,
        JwtTokenDto jwtToken
) {
    public static UserInfoDto create(UserResDto user, JwtTokenDto jwtToken){
        return UserInfoDto.builder()
                .user(user)
                .jwtToken(jwtToken)
                .build();
    }
}
