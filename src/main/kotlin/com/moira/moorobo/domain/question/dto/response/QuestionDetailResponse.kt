package com.moira.moorobo.domain.question.dto.response

import com.moira.moorobo.domain.answer.dto.response.AnswerResponse

data class QuestionDetailResponse(
    val question: QuestionDetailDbResponse,
    val answers: List<AnswerResponse>
)
