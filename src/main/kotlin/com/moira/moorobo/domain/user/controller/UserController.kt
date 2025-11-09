package com.moira.moorobo.domain.user.controller

import com.moira.moorobo.domain.user.dto.request.EmailUpdateRequest
import com.moira.moorobo.domain.user.dto.request.NicknameUpdateRequest
import com.moira.moorobo.domain.user.dto.request.SignupRequest
import com.moira.moorobo.domain.user.dto.response.UserResponse
import com.moira.moorobo.domain.user.service.UserService
import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.auth.dto.SimpleUserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<Nothing> {
        userService.signup(request)

        return ResponseEntity.ok().body(null)
    }

    @GetMapping("/signup/check/nickname")
    fun checkNicknameWhenSignup(@RequestParam nickname: String): ResponseEntity<Nothing> {
        userService.checkNickname(nickname)

        return ResponseEntity.ok().body(null)
    }

    @GetMapping("/signup/check/email")
    fun checkEmailWhenSignup(@RequestParam email: String): ResponseEntity<Nothing> {
        userService.checkEmail(email)

        return ResponseEntity.ok().body(null)
    }

    @GetMapping("/me/simple")
    fun getMySimpleProfile(@UserPrincipal simpleUserAuth: SimpleUserAuth): ResponseEntity<SimpleUserAuth?> {
        return ResponseEntity.ok(simpleUserAuth)
    }

    @GetMapping("/me")
    fun getMyProfile(@UserPrincipal simpleUserAuth: SimpleUserAuth): ResponseEntity<UserResponse> {
        val response = userService.getProfile(simpleUserAuth.userId)

        return ResponseEntity.ok(response)
    }

    @GetMapping("/users/check/nickname")
    fun checkNickname(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @RequestParam nickname: String
    ): ResponseEntity<Nothing> {
        userService.checkNickname(simpleUserAuth, nickname)

        return ResponseEntity.ok().body(null)
    }

    @GetMapping("/users/check/email")
    fun checkEmail(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @RequestParam email: String
    ): ResponseEntity<Nothing> {
        userService.checkEmail(simpleUserAuth, email)

        return ResponseEntity.ok().body(null)
    }

    @PutMapping("/me/nickname")
    fun updateNickname(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @RequestBody request: NicknameUpdateRequest
    ): ResponseEntity<Nothing> {
        userService.updateNickname(simpleUserAuth, request)

        return ResponseEntity.ok().body(null)
    }

    @PutMapping("/me/email")
    fun updateEmail(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @RequestBody request: EmailUpdateRequest
    ): ResponseEntity<Nothing> {
        userService.updateEmail(simpleUserAuth, request)

        return ResponseEntity.ok().body(null)
    }
}