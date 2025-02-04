package com.ktb.stresstest.constant;

import java.util.List;

public class Constants {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String BASIC_PREFIX = "Basic ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REAUTHORIZATION = "refreshToken";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String USER_ROLE_CLAIM_NAME = "role";
    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String USER_EMAIL_CLAIM_NAME = "email";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^])[A-Za-z\\d@$!%*?&#^]{8,20}$";



    public static final List<String> NO_NEED_AUTH_URLS = List.of(
            // 회원가입
            "/api/v1/auth/signin",

            // 로그인
            "/api/auth/signin",
            "/api/auth/login/**",

            // 게시글
            "/api/post",
            "/api/post/**",

            // 댓글
            "/api/comment/**",

            // 헬스체크
            "/api/health/",
            "/api/health/startup",
            "/api/health/liveness",
            "/api/health/readiness"
    );

}