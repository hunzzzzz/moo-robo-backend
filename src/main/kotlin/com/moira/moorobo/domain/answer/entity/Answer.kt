package com.moira.moorobo.domain.answer.entity

import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.user.entity.User
import com.moira.moorobo.global.jpa.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "ANSWER", schema = "MOO_ROBO")
class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    var question: Question,

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    var content: String,

    @Column(name = "ai_answer", nullable = false)
    var aiAnswer: Boolean = false,
) : BaseEntity() {
}