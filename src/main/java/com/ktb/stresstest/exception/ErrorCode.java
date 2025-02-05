package com.ktb.stresstest.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 ERROR
    INVALID_PARAMETER("40000", HttpStatus.BAD_REQUEST, "유효하지 않는 파라미터입니다."),
    MISSING_REQUEST_PARAMETER("40001", HttpStatus.BAD_REQUEST, "필수 파라미터가 누락되었습니다."),
    MISSING_REQUEST_BODY("40002", HttpStatus.BAD_REQUEST, "요청 바디가 누락되었습니다."),
    EMAIL_ERROR("40003", HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다."),
    PASSWORD_ERROR("40004", HttpStatus.BAD_REQUEST, "비밀번호는 8~20자이며, 최소 1개의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."),
    MISSING_REQUEST_IMAGES("40005", HttpStatus.BAD_REQUEST, "이미지가 누락되었습니다."),
    DUPLICATED_EMAIL("40006", HttpStatus.BAD_REQUEST, "해당 이메일로 가입된 계정이 존재합니다."),
    DUPLICATED_NICKNAME("40007", HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    INVALID_LOGIN("40008", HttpStatus.BAD_REQUEST, "로그인 정보가 올바르지 않습니다."),
    TITLE_ERROR("40009", HttpStatus.BAD_REQUEST, "게시물 제목의 길이가 26자를 넘을 수 없습니다."),
    CONTENT_ERROR("40010", HttpStatus.BAD_REQUEST, "게시물 내용의 길이가 100자를 넘을 수 없습니다."),

    // 401 ERROR
    FAILURE_LOGIN("40100", HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다."),
    FAILURE_LOGOUT("40101", HttpStatus.UNAUTHORIZED, "로그아웃에 실패했습니다."),
    INVALID_TOKEN_ERROR("40102", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN_ERROR("40103", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_MALFORMED_ERROR("40104", HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."),
    TOKEN_TYPE_ERROR("40105", HttpStatus.UNAUTHORIZED, "토큰 타입이 일치하지 않습니다."),
    TOKEN_UNSUPPORTED_ERROR("40106", HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
    TOKEN_GENERATION_ERROR("40107", HttpStatus.UNAUTHORIZED, "토큰 생성에 실패하였습니다."),
    TOKEN_UNKNOWN_ERROR("40108", HttpStatus.UNAUTHORIZED, "알 수 없는 토큰입니다."),
    EMPTY_AUTHENTICATION("40109", HttpStatus.UNAUTHORIZED, "인증 정보가 없습니다."),
    INVALID_AUTHORIZATION_HEADER("40110", HttpStatus.UNAUTHORIZED, "유효하지 않은 인증 헤더입니다."),

    // 403 ERROR
    ACCESS_DENIED_ERROR("40300", HttpStatus.FORBIDDEN, "액세스 권한이 없습니다."),

    // 404 ERROR
    NOT_FOUND_USER("40400", HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다."),
    NOT_FOUND_END_POINT("40401", HttpStatus.NOT_FOUND, "존재하지 않는 엔드포인트입니다."),
    NOT_FOUND_POST("40402",HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다."),
    NOT_FOUND_COMMENT("40403",HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."),

    // 415 ERROR
    UNSUPPORTED_MEDIA_TYPE("41500", HttpStatus.UNSUPPORTED_MEDIA_TYPE, "허용되지 않은 파일 형식입니다."),

    // 500 ERROR
    SERVER_ERROR("50000", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
