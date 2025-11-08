package com.moira.moorobo.domain.question.controller

import com.moira.moorobo.domain.question.dto.request.QuestionAddRequest
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.domain.question.service.QuestionService
import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.dto.SimpleUserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}