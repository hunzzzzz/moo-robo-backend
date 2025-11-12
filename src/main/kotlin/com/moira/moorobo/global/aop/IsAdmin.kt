package com.moira.moorobo.global.aop

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
annotation class IsAdmin()
