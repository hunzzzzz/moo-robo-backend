package com.moira.moorobo.domain.user.service

import com.moira.moorobo.domain.user.dto.request.SignupRequest
import com.moira.moorobo.domain.user.repository.UserRepository
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignupService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun checkNickname(nickname: String) {
        if (userRepository.existsUserByNickname(nickname)) {
            throw MooRoboException(ErrorCode.ALREADY_USING_NICKNAME)
        }
    }

    @Transactional(readOnly = true)
    fun checkEmail(email: String) {
        if (userRepository.existsUserByEmail(email)) {
            throw MooRoboException(ErrorCode.ALREADY_USING_EMAIL)
        }
    }

    @Transactional
    fun signup(request: SignupRequest) {
        val user = request.toUser(passwordEncoder)

        userRepository.save(user)
    }
}