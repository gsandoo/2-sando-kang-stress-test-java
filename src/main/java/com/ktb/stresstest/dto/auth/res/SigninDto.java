package com.ktb.stresstest.dto.auth.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SigninDto(
        UserResDto user,
        JwtTokenDto jwtToken
) {
    public static SigninDto create(UserResDto user, JwtTokenDto jwtToken){
        return SigninDto.builder()
                .user(user)
                .jwtToken(jwtToken)
                .build();
    }
}
