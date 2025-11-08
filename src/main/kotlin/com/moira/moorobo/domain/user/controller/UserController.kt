package com.moira.moorobo.domain.user.controller

import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.dto.SimpleUserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {
    @GetMapping("/me/simple")
    fun getMySimpleInfo(@UserPrincipal simpleUserAuth: SimpleUserAuth): ResponseEntity<SimpleUserAuth?> {
        return ResponseEntity.ok(simpleUserAuth)
    }
}