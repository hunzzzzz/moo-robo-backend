package com.moira.moorobo.domain.auth.service

import com.moira.moorobo.domain.user.repository.UserRepository
import com.moira.moorobo.global.dto.SimpleUserAuth
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import com.moira.moorobo.global.utility.CookieHandler
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogoutService(
    private val cookieHandler: CookieHandler,
    private val userRepository: UserRepository
) {
    @Transactional
    fun logout(simpleUserAuth: SimpleUserAuth, httpServletResponse: HttpServletResponse) {
        // [1] userId로 유저 엔티티 조회
        val userId = simpleUserAuth.userId
        val user = userRepository.findUserById(userId)
            ?: throw MooRoboException(ErrorCode.USER_NOT_FOUND)

        // [2] RTK 초기화
        user.rtk = null

        // [3] 쿠키에 담긴 RTK 제거
        cookieHandler.removeRtkFromCookie(response = httpServletResponse)
    }
}