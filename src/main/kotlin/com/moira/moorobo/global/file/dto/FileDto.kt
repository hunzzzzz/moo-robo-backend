package com.moira.moorobo.global.file.dto

import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.question.entity.QuestionFile

data class FileDto(
    val fileId: String,
    val originalFileName: String,
    val storedFileName: String,
    val size: Long,
    val fileUrl: String
) {
    fun toQuestionFile(question: Question): QuestionFile {
        return QuestionFile(
            id = this.fileId,
            question = question,
            originalFileName = this.originalFileName,
            storedFileName = this.storedFileName,
            size = this.size,
            fileUrl = this.fileUrl
        )
    }
}