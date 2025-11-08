package com.moira.moorobo.domain.question.service

import com.moira.moorobo.domain.question.QuestionRepository
import com.moira.moorobo.domain.question.dto.request.QuestionAddRequest
import com.moira.moorobo.domain.question.dto.request.QuestionUpdateRequest
import com.moira.moorobo.domain.question.dto.response.QuestionDetailResponse
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.global.dto.SimpleUserAuth
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import com.moira.moorobo.global.utility.CookieHandler
import com.moira.moorobo.global.utility.EntityFinder
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionService(
    private val cookieHandler: CookieHandler,
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

    @Transactional
    fun getQuestion(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        questionId: Long
    ): QuestionDetailResponse {
        // [1] questionId 쿠키가 존재하지 않으면 조회수 1 증가 및 쿠키 추가
        if (!cookieHandler.hasQuestionIdInCookie(request = httpServletRequest, questionId = questionId)) {
            val question = entityFinder.findQuestionById(questionId)
            question.viewCount += 1

            cookieHandler.putQuestionIdInCookie(response = httpServletResponse, questionId = questionId)
        }

        // [2] 게시글 조회
        return questionRepository.findByQuestionId(questionId)
            ?: throw MooRoboException(ErrorCode.QUESTION_NOT_FOUND)
    }

    @Transactional
    fun updateQuestion(
        simpleUserAuth: SimpleUserAuth,
        request: QuestionUpdateRequest,
        questionId: Long
    ) {
        val question = entityFinder.findQuestionById(questionId)
        val user = entityFinder.findUserById(simpleUserAuth.userId)

        // [1] 권한 확인
        if (question.user != user) {
            throw MooRoboException(ErrorCode.NOT_YOUR_QUESTION)
        }

        // [2] 수정
        question.title = request.title
        question.content = request.content
        question.aiAnswer = request.aiAnswer
    }

    @Transactional
    fun deleteQuestion(simpleUserAuth: SimpleUserAuth, questionId: Long) {
        val question = entityFinder.findQuestionById(questionId)
        val user = entityFinder.findUserById(simpleUserAuth.userId)

        // [1] 권한 확인
        if (question.user != user) {
            throw MooRoboException(ErrorCode.NOT_YOUR_QUESTION)
        }

        // [2] 삭제
        questionRepository.delete(question)
    }
}