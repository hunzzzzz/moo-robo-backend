package com.moira.moorobo.domain.question.repository

import com.moira.moorobo.domain.question.dto.response.QuestionDetailDbResponse
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

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
            (SELECT COUNT(A.id) FROM Answer A WHERE A.question = Q) AS answerCount,
            (SELECT COUNT(QL.id) FROM QuestionLike QL WHERE QL.question = Q) AS likeCount
        )
        FROM Question Q
        INNER JOIN User U ON U.id = Q.user.id
        WHERE Q.user.id = :userId
        ORDER BY Q.createdAt DESC
    """
    )
    fun findMyQuestions(userId: String, pageable: Pageable): Page<QuestionResponse>

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
            U.nickname,
            (SELECT COUNT(QL.id) FROM QuestionLike QL WHERE QL.question = Q) AS likeCount,
            (SELECT COUNT(QL.id) FROM QuestionLike QL WHERE QL.question = Q AND QL.user.id = :userId) AS hasLike
        )
        FROM Question Q
        INNER JOIN User U ON U = Q.user
        WHERE Q.id = :questionId
    """
    )
    fun findQuestionById(questionId: Long, userId: String): QuestionDetailDbResponse?

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
            (SELECT COUNT(A.id) FROM Answer A WHERE A.question = Q) AS answerCount,
            A.likeCount AS likeCount
        )
        FROM Question Q
        INNER JOIN (
            SELECT 
                B.questionId AS questionId,
                B.likeCount AS likeCount,
                RANK() OVER (ORDER BY B.likeCount DESC) AS rankNum
            FROM
            (
                SELECT 
                    QL.question.id AS questionId,
                    COUNT(QL.question.id) AS likeCount
                FROM QuestionLike QL
                WHERE QL.likeAt BETWEEN :startDate AND :endDate
                GROUP BY QL.question.id
            ) B
        ) A
        ON A.questionId = Q.id AND CAST(A.rankNum AS biginteger) = 1
        INNER JOIN User U 
        ON U = Q.user
    """
    )
    fun findWeeklyMostLikedQuestions(startDate: ZonedDateTime, endDate: ZonedDateTime): List<QuestionResponse>

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
            (SELECT COUNT(A.id) FROM Answer A WHERE A.question = Q) AS answerCount,
            A.likeCount AS likeCount
        )
        FROM Question Q
        INNER JOIN (
            SELECT 
                B.questionId AS questionId,
                B.likeCount AS likeCount,
                RANK() OVER (ORDER BY B.likeCount DESC) AS rankNum
            FROM
            (
                SELECT 
                    A.question.id AS questionId,
                    COUNT(A.question.id) AS likeCount
                FROM Answer A
                WHERE A.createdAt BETWEEN :startDate AND :endDate
                GROUP BY A.question.id
            ) B
        ) A
        ON A.questionId = Q.id AND CAST(A.rankNum AS biginteger) = 1
        INNER JOIN User U 
        ON U = Q.user
    """
    )
    fun findWeeklyMostCommentedQuestions(startDate: ZonedDateTime, endDate: ZonedDateTime): List<QuestionResponse>

    fun countByUser(user: User): Int
}