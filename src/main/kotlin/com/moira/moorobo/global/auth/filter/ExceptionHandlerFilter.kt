package com.moira.moorobo.global.auth.filter

import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.ZonedDateTime

@Component
class ExceptionHandlerFilter : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(ExceptionHandlerFilter::class.java)

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

            this.sendErrorResponse(response = response, errorCode = errorCode)
        }
    }
}