package com.moira.moorobo.domain.question.dto.request

import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.user.entity.User

data class QuestionAddRequest(
    val title: String,
    val content: String,
    val aiAnswer: Boolean
) {
    fun toQuestion(user: User): Question {
        return Question(
            user = user,
            title = this.title,
            content = this.content,
            aiAnswer = aiAnswer
        )
    }
}
