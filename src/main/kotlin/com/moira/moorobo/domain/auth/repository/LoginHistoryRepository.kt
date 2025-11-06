package com.moira.moorobo.domain.auth.repository

import com.moira.moorobo.domain.auth.entity.LoginHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoginHistoryRepository : JpaRepository<LoginHistory, Long> {
}