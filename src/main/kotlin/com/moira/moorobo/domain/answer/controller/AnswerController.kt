package com.moira.moorobo.domain.answer.controller

import com.moira.moorobo.domain.answer.dto.request.AnswerAddRequest
import com.moira.moorobo.domain.answer.dto.request.AnswerUpdateRequest
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

    @PostMapping("/questions/{questionId}/answers/ai")
    fun addAiAnswer(
        @RequestBody request: AnswerAddRequest,
        @PathVariable questionId: Long
    ): ResponseEntity<Nothing> {
        answerService.addAnswer(simpleUserAuth = null, request = request, questionId = questionId, isAiAnswer = true)

        return ResponseEntity.ok(null)
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    fun updateAnswer(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @RequestBody request: AnswerUpdateRequest,
        @PathVariable questionId: Long,
        @PathVariable answerId: Long
    ): ResponseEntity<Nothing> {
        answerService.updateAnswer(
            simpleUserAuth = simpleUserAuth,
            request = request,
            questionId = questionId,
            answerId = answerId
        )

        return ResponseEntity.ok(null)
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    fun deleteAnswer(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @PathVariable questionId: Long,
        @PathVariable answerId: Long
    ): ResponseEntity<Nothing> {
        answerService.deleteAnswer(simpleUserAuth = simpleUserAuth, questionId = questionId, answerId = answerId)

        return ResponseEntity.ok(null)
    }
}