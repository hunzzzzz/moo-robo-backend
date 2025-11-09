package com.moira.moorobo.domain.question.dto.request

import org.springframework.web.multipart.MultipartFile

data class QuestionUpdateRequest(
    val title: String,
    val content: String,
    val aiAnswer: Boolean,
    val newFiles: List<MultipartFile>?,
    val deleteFiles: List<String>?
)