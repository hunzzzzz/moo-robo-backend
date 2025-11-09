package com.moira.moorobo.global.auth

import com.moira.moorobo.global.auth.dto.SimpleUserAuth
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.auth.JwtProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.ZonedDateTime

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    companion object {
        val EXCLUDE_REQUEST_MATCHERS = listOf(
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/api/signup/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/signup/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/login/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/questions/{questionId}/answers/ai")
        )
    }

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

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return EXCLUDE_REQUEST_MATCHERS.any { it.matches(request) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("[요청 URI] {} {}", request.method, request.requestURI)

        // [1] Authorization 헤더 값 가져오기
        val authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith("Bearer ")) {
            this.sendErrorResponse(response = response, errorCode = ErrorCode.INVALID_AUTHORIZATION_HEADER)
            return
        }

        // [2] AccessToken 추출
        val atk = jwtProvider.substringToken(authorizationHeaderValue)

        // [3] 토큰 검증
        jwtProvider.validateToken(atk)
            .onSuccess {
                // [4] 유저 정보 추출
                val userId = it.subject
                val email = it.get("email", String::class.java)
                val nickname = it.get("nickname", String::class.java)
                val role = it.get("role", String::class.java)

                log.info("[접속 유저] {}: {}", nickname, email)

                val simpleUserAuth = SimpleUserAuth(
                    userId = userId,
                    email = email,
                    nickname = nickname,
                    role = role
                )

                // [5] Spring Security 권한 및 Authentication 객체 생성
                val authorities = listOf(role).map { SimpleGrantedAuthority(it) }
                val authentication = UsernamePasswordAuthenticationToken(simpleUserAuth, null, authorities)
                SecurityContextHolder.getContext().authentication = authentication

                filterChain.doFilter(request, response)
            }
            .onFailure {
                when (it) {
                    is ExpiredJwtException -> {
                        this.sendErrorResponse(response = response, errorCode = ErrorCode.EXPIRED_ATK)
                        return
                    }

                    is SignatureException -> {
                        this.sendErrorResponse(response = response, errorCode = ErrorCode.INVALID_SIGNATURE)
                        return
                    }

                    is UnsupportedJwtException -> {
                        this.sendErrorResponse(response = response, errorCode = ErrorCode.INVALID_TOKEN)
                        return
                    }

                    is MalformedJwtException -> {
                        this.sendErrorResponse(response = response, errorCode = ErrorCode.INVALID_TOKEN)
                        return
                    }

                    else -> {
                        log.error("e: ", it)
                        this.sendErrorResponse(response = response, errorCode = ErrorCode.INTERNAL_SYSTEM_ERROR)
                        return
                    }
                }
            }
    }
}