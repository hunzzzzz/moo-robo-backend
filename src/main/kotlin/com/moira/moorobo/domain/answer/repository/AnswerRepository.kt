package com.moira.moorobo.domain.answer.repository

import com.moira.moorobo.domain.answer.entity.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepository : JpaRepository<Answer, Long> {
}