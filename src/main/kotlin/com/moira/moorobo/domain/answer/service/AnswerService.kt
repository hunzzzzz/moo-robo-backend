package com.moira.moorobo.domain.answer.service

import com.moira.moorobo.domain.answer.dto.request.AnswerAddRequest
import com.moira.moorobo.domain.answer.dto.request.AnswerUpdateRequest
import com.moira.moorobo.domain.answer.dto.response.AnswerResponse
import com.moira.moorobo.domain.answer.repository.AnswerRepository
import com.moira.moorobo.global.auth.dto.SimpleUserAuth
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import com.moira.moorobo.global.jpa.EntityFinder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnswerService(
    private val answerRepository: AnswerRepository,
    private val entityFinder: EntityFinder
) {
    @Transactional
    fun addAnswer(
        simpleUserAuth: SimpleUserAuth?,
        request: AnswerAddRequest,
        questionId: Long,
        isAiAnswer: Boolean = false
    ) {
        val user = if (isAiAnswer)
            entityFinder.findUserById("ai")
        else
            entityFinder.findUserById(simpleUserAuth?.userId ?: "")
        val question = entityFinder.findQuestionById(questionId)
        val answer = request.toAnswer(user, question, isAiAnswer)

        answerRepository.save(answer)
    }

    @Transactional(readOnly = true)
    fun getAnswers(questionId: Long): List<AnswerResponse> {
        return answerRepository.getAllAnswerByQuestionId(questionId)
    }

    @Transactional
    fun updateAnswer(
        simpleUserAuth: SimpleUserAuth,
        request: AnswerUpdateRequest,
        questionId: Long,
        answerId: Long
    ) {
        val answer = entityFinder.findAnswerById(answerId)

        // [1] 권한 확인
        if (answer.user.id != simpleUserAuth.userId) {
            throw MooRoboException(ErrorCode.NOT_YOUR_ANSWER)
        }

        // [2] 수정
        answer.content = request.content
    }

    @Transactional
    fun deleteAnswer(simpleUserAuth: SimpleUserAuth, questionId: Long, answerId: Long) {
        val answer = entityFinder.findAnswerById(answerId)

        // [1] 권한 확인
        if (answer.user.id != simpleUserAuth.userId) {
            throw MooRoboException(ErrorCode.NOT_YOUR_ANSWER)
        }

        // [2] 삭제
        answerRepository.delete(answer)
    }
}