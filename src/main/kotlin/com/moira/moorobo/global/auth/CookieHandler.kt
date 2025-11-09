package com.moira.moorobo.global.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component

@Component
class CookieHandler {
    /**
     * maxAge           : 쿠키 유효 시간 (단위: 초)
     * path("/")        : 쿠키를 사용할 수 있는 경로
     * httpOnly(true)   : JavaScript에서 접근 불가능
     * secure(true)     : HTTPS 연결에서만 전송
     * sameSite("None") : CORS 문제 해결
     */
    fun hasQuestionIdInCookie(request: HttpServletRequest, questionId: Long): Boolean {
        return request.cookies?.any { it.name == "question$questionId" } ?: false
    }

    fun putQuestionIdInCookie(response: HttpServletResponse, questionId: Long) {
        val cookie = ResponseCookie.from("question$questionId", questionId.toString())
            .maxAge(60 * 60 * 3L)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }

    fun putRtkInCookie(response: HttpServletResponse, rtk: String?) {
        val cookie = ResponseCookie.from("refreshToken", rtk)
            .maxAge(60 * 60 * 24L)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }

    fun removeRtkFromCookie(response: HttpServletResponse) {
        val cookie = ResponseCookie.from("refreshToken")
            .maxAge(0)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }
}