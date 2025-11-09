package com.moira.moorobo.domain.question.repository

import com.moira.moorobo.domain.question.entity.QuestionFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionFileRepository : JpaRepository<QuestionFile, String> {
}