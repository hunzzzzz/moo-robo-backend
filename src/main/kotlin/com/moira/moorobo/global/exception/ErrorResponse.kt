package com.moira.moorobo.global.exception

import java.time.ZonedDateTime

data class ErrorResponse(
    val errorCode: ErrorCode,
    val message: String,
    val time: ZonedDateTime
)
