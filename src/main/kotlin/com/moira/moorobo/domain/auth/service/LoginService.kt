package com.moira.moorobo.domain.auth.service

import com.moira.moorobo.domain.auth.dto.request.LoginRequest
import com.moira.moorobo.domain.user.repository.UserRepository
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
    private val loginHistoryService: LoginHistoryService,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {

    @Transactional
    fun login(
        request: LoginRequest,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ) {
        // [1] HttpServletRequest로부터 유저 접속 정보 추출
        val ipAddress = httpServletRequest.remoteAddr
        val userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT)

        // [2] 이메일로 유저 조회
        val user = userRepository.findUserByEmail(request.email)
            ?: throw MooRoboException(ErrorCode.LOGIN_ERROR)

        // [3-1] 비밀번호가 일치하지 않으면, 로그인 실패 기록 저장
        // [3-2] 비밀번호가 일치하면,       로그인 성공 기록 저장
        if (!passwordEncoder.matches(request.password, user.password)) {
            loginHistoryService.saveFailureHistory(user, ipAddress, userAgent)

            throw MooRoboException(ErrorCode.LOGIN_ERROR)
        } else {
            loginHistoryService.saveSuccessHistory(user, ipAddress, userAgent)
        }

        // TODO: 이후 로직 구현
    }
}