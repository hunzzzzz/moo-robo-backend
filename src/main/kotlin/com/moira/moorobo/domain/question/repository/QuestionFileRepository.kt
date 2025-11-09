package com.moira.moorobo.domain.question.repository

import com.moira.moorobo.domain.question.dto.response.QuestionFileResponse
import com.moira.moorobo.domain.question.entity.QuestionFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface QuestionFileRepository : JpaRepository<QuestionFile, String> {
    @Query(
        """
        SELECT new com.moira.moorobo.domain.question.dto.response.QuestionFileResponse(
            QF.id AS fileId,
            QF.originalFileName,
            QF.storedFileName,
            QF.size,
            QF.fileUrl
        )
        FROM QuestionFile QF
        WHERE QF.question.id = :questionId
    """
    )
    fun findAllQuestionFileByQuestionId(questionId: Long): List<QuestionFileResponse>

    fun findQuestionFileByQuestionIdAndId(questionId: Long, id: String): QuestionFile?
}