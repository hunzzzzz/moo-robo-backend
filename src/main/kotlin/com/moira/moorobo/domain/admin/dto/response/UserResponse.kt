package com.moira.moorobo.domain.admin.dto.response

import com.moira.moorobo.domain.user.entity.UserRole
import java.time.ZonedDateTime

data class UserResponse(
    val userId: String,
    val name: String,
    val nickname: String,
    val email: String,
    val role: UserRole,
    val createdAt: ZonedDateTime,
    val lastLoginAt: ZonedDateTime?,
    val questionCount: Long,
    val answerCount: Long
)
