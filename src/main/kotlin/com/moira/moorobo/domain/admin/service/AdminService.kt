package com.moira.moorobo.domain.admin.service

import com.moira.moorobo.domain.admin.dto.response.UserResponse
import com.moira.moorobo.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val userRepository: UserRepository
) {
    fun getUsers(): List<UserResponse> {
        return userRepository.getUsers()
    }
}