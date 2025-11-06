package com.moira.moorobo.domain.user.entity

import com.moira.moorobo.global.entity.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "USER", schema = "MOO_ROBO")
class User(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: String? = UUID.randomUUID().toString(),

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "nickname", nullable = false, unique = true)
    var nickname: String,

    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: UserRole = UserRole.STUDENT
) : BaseEntity() {
}