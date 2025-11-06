package com.moira.moorobo.domain.auth.controller

import com.moira.moorobo.domain.auth.dto.request.LoginRequest
import com.moira.moorobo.domain.auth.service.LoginService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LoginController(
    private val loginService: LoginService
) {
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<Nothing> {
        loginService.login(request, httpServletRequest, httpServletResponse)

        return ResponseEntity.ok().body(null)
    }
}