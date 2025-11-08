package com.moira.moorobo.domain.question.controller

import com.moira.moorobo.domain.question.dto.request.QuestionAddRequest
import com.moira.moorobo.domain.question.dto.request.QuestionUpdateRequest
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.domain.question.service.QuestionService
import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.dto.SimpleUserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class QuestionController(
    private val questionService: QuestionService
) {
    @PostMapping("/questions")
    fun addQuestion(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @RequestBody request: QuestionAddRequest
    ) {
        questionService.add(simpleUserAuth, request)
    }

    @GetMapping("/me/questions")
    fun getMyQuestions(
        @UserPrincipal simpleUserAuth: SimpleUserAuth
    ): ResponseEntity<List<QuestionResponse>> {
        val list = questionService.getMyQuestions(simpleUserAuth)

        return ResponseEntity.ok(list)
    }

    @PutMapping("/questions/{questionId}")
    fun updateQuestion(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @RequestBody request: QuestionUpdateRequest,
        @PathVariable questionId: Long
    ): ResponseEntity<Nothing> {
        questionService.updateQuestion(simpleUserAuth, request, questionId)

        return ResponseEntity.ok(null)
    }

    @DeleteMapping("/questions/{questionId}")
    fun deleteQuestion(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @PathVariable questionId: Long
    ): ResponseEntity<Nothing> {
        questionService.deleteQuestion(simpleUserAuth, questionId)

        return ResponseEntity.ok(null)
    }
}