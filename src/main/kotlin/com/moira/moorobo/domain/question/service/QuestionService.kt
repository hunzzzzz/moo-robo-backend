package com.moira.moorobo.domain.question.service

import com.moira.moorobo.domain.question.QuestionRepository
import com.moira.moorobo.domain.question.dto.request.QuestionAddRequest
import com.moira.moorobo.domain.user.repository.UserRepository
import com.moira.moorobo.global.dto.SimpleUserAuth
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionService(
    private val questionRepository: QuestionRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun add(simpleUserAuth: SimpleUserAuth, request: QuestionAddRequest) {
        val userId = simpleUserAuth.userId
        val user = userRepository.findUserById(userId) ?: throw MooRoboException(ErrorCode.USER_NOT_FOUND)
        val question = request.toQuestion(user)

        questionRepository.save(question)
    }
}