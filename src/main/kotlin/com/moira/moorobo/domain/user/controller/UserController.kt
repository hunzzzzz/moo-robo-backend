package com.moira.moorobo.domain.user.controller

import com.moira.moorobo.domain.user.dto.request.SignupRequest
import com.moira.moorobo.domain.user.dto.response.UserResponse
import com.moira.moorobo.domain.user.service.UserService
import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.dto.SimpleUserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
    fun checkNickname(@RequestParam nickname: String): ResponseEntity<Nothing> {
        userService.checkNickname(nickname)

        return ResponseEntity.ok().body(null)
    }

    @GetMapping("/signup/check/email")
    fun checkEmail(@RequestParam email: String): ResponseEntity<Nothing> {
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
}