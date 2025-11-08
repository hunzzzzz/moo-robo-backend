package com.moira.moorobo.domain.question.dto.response

import java.time.ZonedDateTime

data class QuestionResponse(
    // question
    val questionId: Long,
    val title: String,
    val content: String,
    val viewCount: Int,
    val aiAnswer: Boolean,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,

    // user
    val userId: String,
    val nickname: String
)
