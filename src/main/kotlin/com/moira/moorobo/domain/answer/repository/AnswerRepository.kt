package com.moira.moorobo.domain.answer.repository

import com.moira.moorobo.domain.answer.dto.response.AnswerResponse
import com.moira.moorobo.domain.answer.entity.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepository : JpaRepository<Answer, Long> {
    @Query(
        """
        SELECT new com.moira.moorobo.domain.answer.dto.response.AnswerResponse(
            A.id AS answerId,
            A.content,
            A.createdAt,
            U.id AS userId,
            U.role,
            U.nickname
        )
        FROM Answer A
        INNER JOIN User U ON U = A.user
        WHERE A.question.id = :questionId
    """
    )
    fun getAllAnswerByQuestionId(questionId: Long): List<AnswerResponse>
}