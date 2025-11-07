package com.moira.moorobo.domain.question.entity

import com.moira.moorobo.domain.user.entity.User
import com.moira.moorobo.global.utility.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "QUESTION", schema = "MOO_ROBO")
class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    var content: String,

    @Column(name = "view_count", nullable = false)
    var viewCount: Int = 0,
) : BaseEntity() {
}