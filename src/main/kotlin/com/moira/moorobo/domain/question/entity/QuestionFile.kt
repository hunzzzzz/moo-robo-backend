package com.moira.moorobo.domain.question.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "QUESTION_FILE", schema = "MOO_ROBO")
class QuestionFile(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: String,

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    var question: Question,

    @Column(name = "original_file_name", nullable = false)
    var originalFileName: String,

    @Column(name = "stored_file_name", nullable = false)
    var storedFileName: String,

    @Column(name = "size", nullable = false)
    var size: Long,

    @Column(name = "file_url", nullable = false)
    var fileUrl: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now()
) {
}