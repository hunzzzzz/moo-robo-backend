package com.moira.moorobo.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: String, val message: String, val httpStatus: HttpStatus) {
    // 시스템 관련 에러코드
    INTERNAL_SYSTEM_ERROR(
        code = "S0001",
        message = "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
    LOCAL_FILE_SYSTEM_ERROR(
        code = "S0002",
        message = "로컬 파일 시스템 환경에서 오류가 발생했습니다.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),

    // 권한 관련 에러코드
    INVALID_AUTHORIZATION_HEADER(
        code = "A0001",
        message = "Authorization 헤더에 토큰 정보가 포함되어 있지 않습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_TOKEN(
        code = "A0002",
        message = "유효하지 않은 토큰입니다.",
        httpStatus = HttpStatus.UNAUTHORIZED
    ),
    EXPIRED_ATK(
        code = "A0003",
        message = "AccessToken이 만료되었습니다.",
        httpStatus = HttpStatus.UNAUTHORIZED
    ),
    INVALID_SIGNATURE(
        code = "A0004",
        message = "토큰 서명이 유효하지 않거나 형식이 올바르지 않습니다.",
        httpStatus = HttpStatus.UNAUTHORIZED
    ),

    // 유저 관련 에러코드
    ALREADY_USING_NICKNAME(
        code = "U0001",
        message = "이미 사용 중인 닉네임입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    ALREADY_USING_EMAIL(
        code = "U0002",
        message = "이미 사용 중인 이메일입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    LOGIN_ERROR(
        code = "U0003",
        message = "이메일 혹은 비밀번호를 잘못 입력하였습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    USER_NOT_FOUND(
        code = "U0004",
        message = "존재하지 않는 유저입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),

    // 질문 관련 에러 코드
    QUESTION_NOT_FOUND(
        code = "Q00001",
        message = "존재하지 않는 질문입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    NOT_YOUR_QUESTION(
        code = "Q00002",
        message = "본인의 질문만 삭제 혹은 수정할 수 있습니다.",
        httpStatus = HttpStatus.UNAUTHORIZED
    ),
    ANSWER_NOT_FOUND(
        code = "Q00003",
        message = "존재하지 않는 답변입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    NOT_YOUR_ANSWER(
        code = "Q00004",
        message = "본인의 답변만 삭제 혹은 수정할 수 있습니다.",
        httpStatus = HttpStatus.UNAUTHORIZED
    ),
    EXCEEDED_MAX_FILE_COUNT(
        code = "Q00005",
        message = "최대 10개까지의 파일만 첨부할 수 있습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    FILE_SIZE_LIMIT_EXCEEDED(
        code = "Q00006",
        message = "최대 5MB 크기의 파일만 첨부할 수 있습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    UNSUPPORTED_FILE_EXTENSION(
        code = "Q00007",
        message = "지원하지 않는 파일 확장자입니다. (허용 확장자: jpg, jpeg, png, pdf)",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_FILE(
        code = "Q0008",
        message = "유효하지 않은 파일입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    FILE_SAVE_FAILED(
        code = "Q0009",
        message = "파일 저장에 실패하였습니다. 다시 시도해주세요.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
    FILE_LOAD_FAILED(
        code = "Q0010",
        message = "파일 로드에 실패하였습니다. 새로고침 후 다시 시도해주세요.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
    FILE_DELETE_FAILED(
        code = "Q0011",
        message = "파일 삭제에 실패하였습니다.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
    FILE_NOT_FOUND(
        code = "Q0012",
        message = "존재하지 않는 파일입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    FILE_DELETE_FORBIDDEN(
        code = "Q0013",
        message = "해당 디렉터리 경로에 접근할 수 없습니다.",
        httpStatus = HttpStatus.FORBIDDEN
    ),
    LIKE_NOT_FOUND(
        code = "Q0014",
        message = "존재하지 않는 좋아요 정보입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    )
}