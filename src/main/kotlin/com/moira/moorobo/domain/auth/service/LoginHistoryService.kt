package com.moira.moorobo.domain.auth.service

import com.moira.moorobo.domain.auth.entity.LoginHistory
import com.moira.moorobo.domain.auth.repository.LoginHistoryRepository
import com.moira.moorobo.domain.user.entity.User
import com.moira.moorobo.global.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class LoginHistoryService(
    private val loginHistoryRepository: LoginHistoryRepository,
) {
    @Transactional(propagation = Propagation.REQUIRED)
    fun saveSuccessHistory(user: User, ipAddress: String, userAgent: String) {
        val loginHistory = LoginHistory(
            user = user,
            success = true,
            failureReason = null,
            ipAddress = ipAddress,
            userAgent = userAgent
        )
        loginHistoryRepository.save(loginHistory)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun saveFailureHistory(user: User, ipAddress: String, userAgent: String) {
        val loginHistory = LoginHistory(
            user = user,
            success = false,
            failureReason = ErrorCode.LOGIN_ERROR.name,
            ipAddress = ipAddress,
            userAgent = userAgent
        )
        loginHistoryRepository.save(loginHistory)
    }
}