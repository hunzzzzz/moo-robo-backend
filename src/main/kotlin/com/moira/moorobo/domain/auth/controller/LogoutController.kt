package com.moira.moorobo.domain.auth.controller

import com.moira.moorobo.domain.auth.service.LogoutService
import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.dto.SimpleUserAuth
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LogoutController(
    private val logoutService: LogoutService
) {
    @PostMapping("/logout")
    fun logout(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<Nothing> {
        logoutService.logout(simpleUserAuth, httpServletResponse)

        return ResponseEntity.ok().body(null)
    }
}