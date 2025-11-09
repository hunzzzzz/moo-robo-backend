package com.moira.moorobo.domain.user.repository

import com.moira.moorobo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun existsUserByNickname(nickname: String): Boolean
    fun existsUserByEmail(email: String): Boolean

    fun findUserByEmail(email: String): User?

    fun existsUserByNicknameAndIdNot(nickname: String, userId: String): Boolean
    fun existsUserByEmailAndIdNot(email: String, userId: String): Boolean
}