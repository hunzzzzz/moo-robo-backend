package com.moira.moorobo.domain.user.controller

import com.moira.moorobo.domain.user.dto.request.SignupRequest
import com.moira.moorobo.domain.user.service.SignupService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class SignupController(
    private val signupService: SignupService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<Nothing> {
        signupService.signup(request)

        return ResponseEntity.ok().body(null)
    }

    @GetMapping("/signup/check/nickname")
    fun checkNickname(@RequestParam nickname: String): ResponseEntity<Nothing> {
        signupService.checkNickname(nickname)

        return ResponseEntity.ok().body(null)
    }

    @GetMapping("/signup/check/email")
    fun checkEmail(@RequestParam email: String): ResponseEntity<Nothing> {
        signupService.checkEmail(email)

        return ResponseEntity.ok().body(null)
    }
}