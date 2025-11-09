package com.moira.moorobo.domain.question.repository

import com.moira.moorobo.domain.question.dto.response.QuestionDetailDbResponse
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {
    @Query(
        """
        SELECT new com.moira.moorobo.domain.question.dto.response.QuestionResponse(
            Q.id AS questionId,
            Q.title,
            Q.content,
            Q.viewCount,
            Q.createdAt,
            Q.updatedAt,
            U.id AS userId,
            U.nickname,
            (SELECT COUNT(A.id) FROM Answer A WHERE A.question = Q) AS answerCount
        )
        FROM Question Q
        INNER JOIN User U ON U.id = Q.user.id
        WHERE Q.user.id = :userId
        ORDER BY Q.createdAt DESC
    """
    )
    fun findAllQuestionsByUserId(userId: String): List<QuestionResponse>

    @Query(
        """
        SELECT new com.moira.moorobo.domain.question.dto.response.QuestionDetailDbResponse(
            Q.id AS questionId,
            Q.title,
            Q.content,
            Q.aiAnswer,
            Q.viewCount,
            Q.createdAt,
            Q.updatedAt,
            U.id AS userId,
            U.nickname
        )
        FROM Question Q
        INNER JOIN User U ON U = Q.user
        WHERE Q.id = :questionId
    """
    )
    fun findQuestionById(questionId: Long): QuestionDetailDbResponse?

    fun countByUser(user: User): Int
}