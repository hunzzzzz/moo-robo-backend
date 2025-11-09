package com.moira.moorobo.domain.question.controller

import com.moira.moorobo.domain.question.dto.request.QuestionAddRequest
import com.moira.moorobo.domain.question.dto.request.QuestionUpdateRequest
import com.moira.moorobo.domain.question.dto.response.QuestionDetailResponse
import com.moira.moorobo.domain.question.dto.response.QuestionIdResponse
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.domain.question.service.QuestionService
import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.dto.SimpleUserAuth
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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
        @ModelAttribute request: QuestionAddRequest
    ): ResponseEntity<QuestionIdResponse> {
        val questionIdResponse = questionService.add(simpleUserAuth, request)

        return ResponseEntity.ok(questionIdResponse)
    }

    @GetMapping("/me/questions")
    fun getMyQuestions(
        @UserPrincipal simpleUserAuth: SimpleUserAuth
    ): ResponseEntity<List<QuestionResponse>> {
        val list = questionService.getMyQuestions(simpleUserAuth)

        return ResponseEntity.ok(list)
    }

    @GetMapping("/questions/{questionId}")
    fun getQuestion(
        @PathVariable questionId: Long,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<QuestionDetailResponse> {
        val response = questionService.getQuestion(
            questionId = questionId,
            httpServletRequest = httpServletRequest,
            httpServletResponse = httpServletResponse
        )

        return ResponseEntity.ok(response)
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