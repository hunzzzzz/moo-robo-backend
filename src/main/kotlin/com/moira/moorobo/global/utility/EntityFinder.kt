package com.moira.moorobo.global.utility

import com.moira.moorobo.domain.user.repository.UserRepository
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class EntityFinder(
    private val userRepository: UserRepository
) {
    fun findUserById(userId: String) = userRepository.findByIdOrNull(userId)
        ?: throw MooRoboException(ErrorCode.USER_NOT_FOUND)

    fun findUserByEmail(email: String) = userRepository.findUserByEmail(email)
        ?: throw MooRoboException(ErrorCode.USER_NOT_FOUND)
}