package com.moira.moorobo.domain.like.service

import com.moira.moorobo.domain.like.entity.QuestionLike
import com.moira.moorobo.domain.like.repository.QuestionLikeRepository
import com.moira.moorobo.global.dto.SimpleUserAuth
import com.moira.moorobo.global.utility.EntityFinder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionLikeService(
    private val entityFinder: EntityFinder,
    private val questionLikeRepository: QuestionLikeRepository
) {
    @Transactional
    fun like(simpleUserAuth: SimpleUserAuth, questionId: Long) {
        val user = entityFinder.findUserById(simpleUserAuth.userId)
        val question = entityFinder.findQuestionById(questionId)

        // [1] 해당 게시글에 이미 좋아요를 눌렀는지 확인
        val hasLike = questionLikeRepository.existsByUserAndQuestion(user = user, question = question)

        // [2-1] 좋아요 정보가 있으면 삭제
        if (hasLike) {
            val questionLike = entityFinder.findQuestionLikeByUserAndQuestion(user, question)
            questionLikeRepository.delete(questionLike)
        }
        // [2-2] 좋아요 정보가 없으면 저장
        else {
            val questionLike = QuestionLike(user = user, question = question)
            questionLikeRepository.save(questionLike)
        }
    }
}