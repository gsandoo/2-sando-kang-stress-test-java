package com.ktb.stresstest.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EUserRole {
    USER("USER", "ROLE_USER"),
    ADMIN("ADMIN", "ROLE_ADMIN");

    private final String name;
    private final String securityName;
}