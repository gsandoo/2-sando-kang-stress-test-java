package com.ktb.stresstest.security.info;

import com.ktb.stresstest.type.EUserRole;
import lombok.Builder;

@Builder
public record JwtUserInfo(Long id, EUserRole role) {
}

