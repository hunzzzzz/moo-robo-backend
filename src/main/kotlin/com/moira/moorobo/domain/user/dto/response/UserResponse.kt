package com.moira.moorobo.domain.user.dto.response

import com.moira.moorobo.domain.user.entity.User
import java.time.ZonedDateTime

data class UserResponse(
    // user
    val userId: String,
    val email: String,
    val role: String,
    val name: String,
    val nickname: String,
    val lastLoginAt: ZonedDateTime?,
    val createdAt: ZonedDateTime,

    // question
    val questionCount: Int
) {
    companion object {
        fun from(user: User, questionCount: Int): UserResponse {
            return UserResponse(
                userId = user.id,
                email = user.email,
                role = user.role.toString(),
                name = user.name,
                nickname = user.nickname,
                lastLoginAt = user.lastLoginAt,
                createdAt = user.createdAt,
                questionCount = questionCount
            )
        }
    }
}
