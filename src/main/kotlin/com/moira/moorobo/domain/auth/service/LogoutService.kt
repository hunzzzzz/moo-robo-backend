package com.moira.moorobo.domain.auth.service

import com.moira.moorobo.global.dto.SimpleUserAuth
import com.moira.moorobo.global.utility.CookieHandler
import com.moira.moorobo.global.utility.EntityFinder
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogoutService(
    private val cookieHandler: CookieHandler,
    private val entityFinder: EntityFinder
) {
    @Transactional
    fun logout(simpleUserAuth: SimpleUserAuth, httpServletResponse: HttpServletResponse) {
        // [1] userId로 유저 엔티티 조회
        val user = entityFinder.findUserById(simpleUserAuth.userId)

        // [2] RTK 초기화
        user.rtk = null

        // [3] 쿠키에 담긴 RTK 제거
        cookieHandler.removeRtkFromCookie(response = httpServletResponse)
    }
}