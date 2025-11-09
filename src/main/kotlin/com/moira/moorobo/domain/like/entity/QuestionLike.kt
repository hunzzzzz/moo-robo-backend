package com.moira.moorobo.domain.like.entity

import com.moira.moorobo.domain.question.entity.Question
import com.moira.moorobo.domain.user.entity.User
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "QUESTION_LIKE", schema = "MOO_ROBO")
class QuestionLike(
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

    @Column(name = "like_at", nullable = false)
    var likeAt: ZonedDateTime = ZonedDateTime.now()
) {
}