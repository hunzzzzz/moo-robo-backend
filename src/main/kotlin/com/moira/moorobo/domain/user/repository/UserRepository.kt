package com.moira.moorobo.domain.user.repository

import com.moira.moorobo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun existsUserByNickname(nickname: String): Boolean
    fun existsUserByEmail(email: String): Boolean
}