package com.moira.moorobo.domain.user.repository

import com.moira.moorobo.domain.admin.dto.response.UserResponse
import com.moira.moorobo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun existsUserByNickname(nickname: String): Boolean
    fun existsUserByEmail(email: String): Boolean

    fun findUserByEmail(email: String): User?

    fun existsUserByNicknameAndIdNot(nickname: String, userId: String): Boolean
    fun existsUserByEmailAndIdNot(email: String, userId: String): Boolean

    @Query("""
        SELECT new com.moira.moorobo.domain.admin.dto.response.UserResponse(
            U.id AS userId,
            U.name,
            U.nickname,
            U.email,
            U.role,
            U.createdAt,
            U.lastLoginAt,
            (SELECT COUNT(Q.id) FROM Question Q WHERE Q.user = U) AS questionCount,
            (SELECT COUNT(A.id) FROM Answer A WHERE A.user = U) AS answerCount
        )
        FROM User U
        WHERE U.id != 'ai'
    """)
    fun getUsers(): List<UserResponse>
}