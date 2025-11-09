package com.moira.moorobo.domain.like.controller

import com.moira.moorobo.domain.like.service.QuestionLikeService
import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.dto.SimpleUserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class QuestionLikeController(
    private val questionLikeService: QuestionLikeService
) {
    @PostMapping("/questions/{questionId}/like")
    fun like(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @PathVariable questionId: Long
    ): ResponseEntity<Nothing> {
        questionLikeService.like(simpleUserAuth = simpleUserAuth, questionId = questionId)

        return ResponseEntity.ok(null)
    }
}