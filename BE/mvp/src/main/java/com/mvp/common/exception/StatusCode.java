package com.mvp.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor

public enum StatusCode {

    SUCCESS(HttpStatus.OK, "정상적으로 요청이 완료되었습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not found."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 Request Method 호출입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"),
    NO_SUCH_ELEMENT(HttpStatus.BAD_REQUEST,"존재하지 않는 요소입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일 입니다."),
    INVALID_EMAIL_OR_PASSWORD(HttpStatus.BAD_REQUEST,"이메일 혹은 비밀번호가 일치하지 않습니다"),
    NO_TARGET_VEHICLE(HttpStatus.BAD_REQUEST, "해당 차량이 존재하지 않습니다."),
    NO_AVAILABLE_LOT(HttpStatus.BAD_REQUEST, "주차 가능한 주차 공간이 없습니다."),
    NO_ALLOCATED_LOT(HttpStatus.BAD_REQUEST, "해당 차량에 할당된 주차 공간이 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}