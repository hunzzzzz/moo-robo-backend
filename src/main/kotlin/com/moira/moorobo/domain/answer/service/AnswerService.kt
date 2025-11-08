package com.moira.moorobo.domain.answer.service

import com.moira.moorobo.domain.answer.dto.request.AnswerAddRequest
import com.moira.moorobo.domain.answer.repository.AnswerRepository
import com.moira.moorobo.global.dto.SimpleUserAuth
import com.moira.moorobo.global.utility.EntityFinder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnswerService(
    private val answerRepository: AnswerRepository,
    private val entityFinder: EntityFinder
) {
    @Transactional
    fun addAnswer(
        simpleUserAuth: SimpleUserAuth,
        request: AnswerAddRequest,
        questionId: Long
    ) {
        val user = entityFinder.findUserById(simpleUserAuth.userId)
        val question = entityFinder.findQuestionById(questionId)
        val answer = request.toAnswer(user, question)

        answerRepository.save(answer)
    }
}