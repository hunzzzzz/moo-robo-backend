package com.moira.moorobo.domain.user.dto.request

import com.moira.moorobo.domain.user.entity.User
import org.springframework.security.crypto.password.PasswordEncoder

data class SignupRequest(
    val name: String,
    val nickname: String,
    val email: String,
    val password: String
) {
    fun toUser(passwordEncoder: PasswordEncoder): User {
        return User(
            name = this.name,
            nickname = this.nickname,
            email = this.email,
            password = passwordEncoder.encode(this.password),
        )
    }
}