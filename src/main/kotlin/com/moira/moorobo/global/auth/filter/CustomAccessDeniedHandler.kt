package com.moira.moorobo.global.auth.filter

import com.moira.moorobo.global.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
    private val filterErrorSender: FilterErrorSender
) : AccessDeniedHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        log.warn("Access Denied: {} {}", request.method, request.requestURI)

        filterErrorSender.sendErrorResponse(response = response, errorCode = ErrorCode.ACCESS_DENIED)
    }
}