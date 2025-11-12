package com.moira.moorobo.global.auth.filter

import com.moira.moorobo.global.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    private fun sendErrorResponse(
        response: HttpServletResponse,
        errorCode: ErrorCode
    ) {
        response.status = errorCode.httpStatus.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        val errorResponse = """
            {"message": "${errorCode.message}", "errorCode": "${errorCode.code}", "time": "${ZonedDateTime.now()}"}
        """.trimIndent()

        response.writer.write(errorResponse)
    }

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        log.warn("Access Denied: {} {}", request.method, request.requestURI)

        this.sendErrorResponse(response = response, errorCode = ErrorCode.ACCESS_DENIED)
    }

}