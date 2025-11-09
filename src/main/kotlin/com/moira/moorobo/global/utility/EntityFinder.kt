package com.moira.moorobo.global.utility

import com.moira.moorobo.domain.answer.repository.AnswerRepository
import com.moira.moorobo.domain.like.repository.QuestionLikeRepository
import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.question.repository.QuestionFileRepository
import com.moira.moorobo.domain.question.repository.QuestionRepository
import com.moira.moorobo.domain.user.entity.User
import com.moira.moorobo.domain.user.repository.UserRepository
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class EntityFinder(
    private val answerRepository: AnswerRepository,
    private val questionFileRepository: QuestionFileRepository,
    private val questionLikeRepository: QuestionLikeRepository,
    private val questionRepository: QuestionRepository,
    private val userRepository: UserRepository
) {
    fun findUserById(userId: String) = userRepository.findByIdOrNull(userId)
        ?: throw MooRoboException(ErrorCode.USER_NOT_FOUND)

    fun findUserByEmail(email: String) = userRepository.findUserByEmail(email)
        ?: throw MooRoboException(ErrorCode.USER_NOT_FOUND)

    fun findQuestionById(questionId: Long) = questionRepository.findByIdOrNull(questionId)
        ?: throw MooRoboException(ErrorCode.QUESTION_NOT_FOUND)

    fun findQuestionFileByQuestionIdAndId(questionId: Long, fileId: String) =
        questionFileRepository.findQuestionFileByQuestionIdAndId(questionId = questionId, id = fileId)
            ?: throw MooRoboException(ErrorCode.FILE_NOT_FOUND)

    fun findQuestionLikeByUserAndQuestion(user: User, question: Question) =
        questionLikeRepository.findByUserAndQuestion(user = user, question = question)
            ?: throw MooRoboException(ErrorCode.LIKE_NOT_FOUND)

    fun findAnswerById(answerId: Long) = answerRepository.findByIdOrNull(answerId)
        ?: throw MooRoboException(ErrorCode.ANSWER_NOT_FOUND)
}