package com.moira.moorobo.global.auth.dto

data class SimpleUserAuth(
    val userId: String,
    val email: String,
    val role: String,
    val nickname: String
)