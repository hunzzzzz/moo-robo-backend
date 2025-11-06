package com.moira.moorobo.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.ZonedDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handleMooRoboException(e: MooRoboException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(errorCode = e.errorCode, message = e.message ?: "알 수 없는 오류입니다.", time = ZonedDateTime.now())

        return ResponseEntity.status(e.errorCode.httpStatus).body(body)
    }
}