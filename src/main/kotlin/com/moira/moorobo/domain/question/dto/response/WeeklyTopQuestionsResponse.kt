package com.moira.moorobo.domain.question.dto.response

data class WeeklyTopQuestionsResponse(
    val mostLiked: QuestionResponse,
    val mostCommented: QuestionResponse
)
