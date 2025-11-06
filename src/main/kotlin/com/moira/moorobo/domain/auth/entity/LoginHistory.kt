package com.moira.moorobo.domain.auth.entity

import com.moira.moorobo.domain.user.entity.User
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "LOGIN_HISTORY", schema = "MOO_ROBO")
class LoginHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "success", nullable = false)
    var success: Boolean,

    @Column(name = "failure_reason", nullable = true)
    var failureReason: String? = null,

    @Column(name = "ip_address", nullable = false)
    var ipAddress: String,

    @Column(name = "user_agent", nullable = false)
    var userAgent: String,

    @Column(name = "login_at", nullable = false)
    var loginAt: ZonedDateTime = ZonedDateTime.now()
) {
}