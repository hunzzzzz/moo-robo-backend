package com.moira.moorobo.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: String, val message: String, val httpStatus: HttpStatus) {
    // 시스템 관련 에러코드
    INTERNAL_SYSTEM_ERROR(
        code = "S0001",
        message = "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
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
}