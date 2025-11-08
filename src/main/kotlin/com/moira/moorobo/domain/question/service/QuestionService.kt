package com.moira.moorobo.domain.question.service

import com.moira.moorobo.domain.question.QuestionRepository
import com.moira.moorobo.domain.question.dto.request.QuestionAddRequest
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.global.dto.SimpleUserAuth
import com.moira.moorobo.global.utility.EntityFinder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionService(
    private val entityFinder: EntityFinder,
    private val questionRepository: QuestionRepository
) {
    @Transactional
    fun add(simpleUserAuth: SimpleUserAuth, request: QuestionAddRequest) {
        val user = entityFinder.findUserById(simpleUserAuth.userId)
        val question = request.toQuestion(user)

        questionRepository.save(question)
    }

    @Transactional(readOnly = true)
    fun getMyQuestions(simpleUserAuth: SimpleUserAuth): List<QuestionResponse> {
        val user = entityFinder.findUserById(simpleUserAuth.userId)

        return questionRepository.findAllByUser(user)
    }
}