package com.moira.moorobo.global.dto

data class SimpleUserAuth(
    val userId: String,
    val email: String,
    val role: String,
    val nickname: String
)