package com.moira.moorobo.global.auth.filter

import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ExceptionHandlerFilter(
    private val filterErrorSender: FilterErrorSender
) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(ExceptionHandlerFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            val errorCode = when (e) {
                // 커스텀 예외는 그대로 사용
                is MooRoboException -> e.errorCode
                // RuntimeException 등 일반 예외는 내부 시스템 오류로 매핑
                else -> {
                    log.error("[예외 발생] ${e.message}")
                    ErrorCode.INTERNAL_SYSTEM_ERROR
                }
            }

            filterErrorSender.sendErrorResponse(response = response, errorCode = errorCode)
        }
    }
}