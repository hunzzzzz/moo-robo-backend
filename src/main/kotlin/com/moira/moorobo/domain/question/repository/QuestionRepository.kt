package com.moira.moorobo.domain.question.repository

import com.moira.moorobo.domain.question.dto.response.QuestionDetailResponse
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {
    @Query(
        "SELECT new com.moira.moorobo.domain.question.dto.response.QuestionResponse(" +
                "Q.id AS questionId," +
                "Q.title AS title, " +
                "Q.content AS content, " +
                "Q.viewCount AS viewCount, " +
                "Q.createdAt AS createdAt, " +
                "Q.updatedAt AS updatedAt, " +
                "U.id AS userId, " +
                "U.nickname AS nickname " +
                ") " +
                "FROM Question Q " +
                "INNER JOIN User U ON U = Q.user " +
                "WHERE Q.user = :user"
    )
    fun findAllByUser(user: User): List<QuestionResponse>

    @Query(
        "SELECT new com.moira.moorobo.domain.question.dto.response.QuestionDetailResponse(" +
                "Q.id AS questionId," +
                "Q.title AS title, " +
                "Q.content AS content, " +
                "Q.aiAnswer AS aiAnswer, " +
                "Q.viewCount AS viewCount, " +
                "Q.createdAt AS createdAt, " +
                "Q.updatedAt AS updatedAt, " +
                "U.id AS userId, " +
                "U.nickname AS nickname " +
                ") " +
                "FROM Question Q " +
                "INNER JOIN User U ON U = Q.user " +
                "WHERE Q.id = :questionId"
    )
    fun findByQuestionId(questionId: Long): QuestionDetailResponse?

    fun countByUser(user: User): Int
}