package com.moira.moorobo.domain.question.dto.response

data class QuestionFileResponse(
    val fileId: String,
    val originalFileName: String,
    val storedFileName: String,
    val size: Long,
    val fileUrl: String
)