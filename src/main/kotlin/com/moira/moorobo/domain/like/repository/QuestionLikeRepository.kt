package com.moira.moorobo.domain.like.repository

import com.moira.moorobo.domain.like.entity.QuestionLike
import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionLikeRepository : JpaRepository<QuestionLike, Long> {
    fun existsByUserAndQuestion(user: User, question: Question): Boolean

    fun findByUserAndQuestion(user: User, question: Question): QuestionLike?
}