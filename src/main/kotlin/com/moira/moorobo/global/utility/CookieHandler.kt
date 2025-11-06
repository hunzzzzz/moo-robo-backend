package com.moira.moorobo.global.utility

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class CookieHandler {
    fun putRtkInCookie(response: HttpServletResponse, rtk: String?) {
        val cookie = Cookie("refreshToken", rtk)

        // cookie.setSecure(true);   // HTTPS 연결에서만 전송 (운영 환경에서는 주석 해제)
        cookie.isHttpOnly = true     // JavaScript로 접근 불가능
        cookie.path = "/"            // 모든 경로에서 쿠키 사용 가능
        cookie.maxAge = 60 * 60 * 24 // 1일

        response.addCookie(cookie)
    }

    fun removeRtkFromCookie(response: HttpServletResponse) {
        val cookie = Cookie("refreshToken", null)

        // cookie.setSecure(true); // HTTPS 연결에서만 전송 (운영 환경에서는 주석 해제)
        cookie.isHttpOnly = true   // JavaScript로 접근 불가능
        cookie.path = "/"          // 모든 경로에서 쿠키 사용 가능
        cookie.maxAge = 0          // 쿠키 만료

        response.addCookie(cookie)
    }
}