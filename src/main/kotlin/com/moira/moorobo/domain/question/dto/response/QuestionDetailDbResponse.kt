package com.moira.moorobo.domain.question.dto.response

import java.time.ZonedDateTime

data class QuestionDetailDbResponse(
    // question
    val questionId: Long,
    val title: String,
    val content: String,
    val aiAnswer: Boolean,
    val viewCount: Int,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,

    // user
    val userId: String,
    val nickname: String

    // TODO
    // comment
)
