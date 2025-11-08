package com.moira.moorobo.domain.question.dto.request

data class QuestionUpdateRequest(
    val title: String,
    val content: String,
    val aiAnswer: Boolean
)