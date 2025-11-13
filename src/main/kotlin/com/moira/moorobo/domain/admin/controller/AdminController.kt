package com.moira.moorobo.domain.admin.controller

import com.moira.moorobo.domain.admin.dto.response.UserResponse
import com.moira.moorobo.domain.admin.service.AdminService
import com.moira.moorobo.global.aop.IsAdmin
import com.moira.moorobo.global.aop.IsAuthenticated
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AdminController(
    private val adminService: AdminService
) {
    @IsAdmin
    @GetMapping("/admin/users")
    fun getUsers(): ResponseEntity<List<UserResponse>> {
        val users = adminService.getUsers()

        return ResponseEntity.ok(users)
    }
}