package com.moira.moorobo.domain.answer.dto.request

import com.moira.moorobo.domain.answer.entity.Answer
import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.user.entity.User

data class AnswerAddRequest(
    val content: String
) {
    fun toAnswer(user: User, question: Question): Answer {
        return Answer(user = user, question = question, content = this.content)
    }
}