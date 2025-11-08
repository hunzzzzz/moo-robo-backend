package com.moira.moorobo.domain.answer.controller

import com.moira.moorobo.domain.answer.dto.request.AnswerAddRequest
import com.moira.moorobo.domain.answer.service.AnswerService
import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.dto.SimpleUserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AnswerController(
    private val answerService: AnswerService
) {
    @PostMapping("/questions/{questionId}/answers")
    fun addAnswer(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @RequestBody request: AnswerAddRequest,
        @PathVariable questionId: Long,
    ): ResponseEntity<Nothing> {
        answerService.addAnswer(simpleUserAuth = simpleUserAuth, request = request, questionId = questionId)

        return ResponseEntity.ok(null)
    }
}