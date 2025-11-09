package com.moira.moorobo.global.file.dto

import org.springframework.core.io.Resource

data class FileDownloadDto(
    val encodedFileName: String,
    val resource: Resource
)
