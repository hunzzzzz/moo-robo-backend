package com.moira.moorobo.domain.answer.dto.response

import java.time.ZonedDateTime

data class AnswerResponse(
    // answer
    val answerId: Long,
    val content: String,
    val createdAt: ZonedDateTime,

    // user
    val userId: String,
    val nickname: String
)
