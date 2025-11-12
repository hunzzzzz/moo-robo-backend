package com.moira.moorobo.global.aop

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@PreAuthorize("isAuthenticated()")
annotation class IsAuthenticated