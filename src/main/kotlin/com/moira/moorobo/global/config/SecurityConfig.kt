package com.moira.moorobo.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // 1. CSRF, CORS, Login, Basic 인증 비활성화
            .csrf { it.disable() }
            .cors { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            // 2. 세션 관리: STATELESS (JWT 기반이므로)
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            // 3. 인가 규칙 설정
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(HttpMethod.GET, "/api/signup/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/signup/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                    .anyRequest().authenticated()
            }

        return http.build()
    }
}