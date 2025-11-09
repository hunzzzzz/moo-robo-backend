package com.moira.moorobo.domain.user.service

import com.moira.moorobo.domain.question.repository.QuestionRepository
import com.moira.moorobo.domain.user.dto.request.EmailUpdateRequest
import com.moira.moorobo.domain.user.dto.request.NicknameUpdateRequest
import com.moira.moorobo.domain.user.dto.request.SignupRequest
import com.moira.moorobo.domain.user.dto.response.UserResponse
import com.moira.moorobo.domain.user.repository.UserRepository
import com.moira.moorobo.global.auth.dto.SimpleUserAuth
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import com.moira.moorobo.global.jpa.EntityFinder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val entityFinder: EntityFinder,
    private val passwordEncoder: PasswordEncoder,
    private val questionRepository: QuestionRepository,
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

    @Transactional(readOnly = true)
    fun getProfile(userId: String): UserResponse {
        val user = entityFinder.findUserById(userId)
        val questionCount = questionRepository.countByUser(user = user)

        return UserResponse.from(user = user, questionCount = questionCount)
    }

    @Transactional(readOnly = true)
    fun checkNickname(simpleUserAuth: SimpleUserAuth, nickname: String) {
        if (userRepository.existsUserByNicknameAndIdNot(nickname, simpleUserAuth.userId)) {
            throw MooRoboException(ErrorCode.ALREADY_USING_NICKNAME)
        }
    }

    @Transactional(readOnly = true)
    fun checkEmail(simpleUserAuth: SimpleUserAuth, email: String) {
        if (userRepository.existsUserByEmailAndIdNot(email, simpleUserAuth.userId)) {
            throw MooRoboException(ErrorCode.ALREADY_USING_EMAIL)
        }
    }

    @Transactional
    fun updateNickname(simpleUserAuth: SimpleUserAuth, request: NicknameUpdateRequest) {
        val user = entityFinder.findUserById(simpleUserAuth.userId)

        user.nickname = request.nickname
    }

    @Transactional
    fun updateEmail(simpleUserAuth: SimpleUserAuth, request: EmailUpdateRequest) {
        val user = entityFinder.findUserById(simpleUserAuth.userId)

        user.email = request.email
    }
}