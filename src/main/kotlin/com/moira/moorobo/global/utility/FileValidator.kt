package com.moira.moorobo.global.utility

import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class FileValidator {
    companion object {
        const val MAX_FILE_COUNT = 10
        const val MAX_FILE_SIZE = 5L * 1024 * 1024 // 5MB
        private val ALLOWED_EXTENSIONS = setOf("jpg", "jpeg", "png", "pdf")
    }

    private fun validateFileCount(files: List<MultipartFile>) {
        if (files.size > MAX_FILE_COUNT) {
            throw MooRoboException(ErrorCode.EXCEEDED_MAX_FILE_COUNT)
        }
    }

    private fun validateFileSize(file: MultipartFile) {
        if (file.isEmpty || file.size > MAX_FILE_SIZE) {
            throw MooRoboException(ErrorCode.FILE_SIZE_LIMIT_EXCEEDED)
        }
    }

    private fun validateFileExtension(file: MultipartFile) {
        val originalFileName = file.originalFilename ?: ""
        val fileExtension = originalFileName.substringAfterLast(".", "").lowercase()

        if (fileExtension.isEmpty() || fileExtension !in ALLOWED_EXTENSIONS) {
            throw MooRoboException(ErrorCode.UNSUPPORTED_FILE_EXTENSION)
        }
    }

    fun validateFiles(files: List<MultipartFile>) {
        this.validateFileCount(files = files)       // 파일 개수 검증

        files.forEach { file ->
            this.validateFileSize(file = file)      // 파일 크기 검증
            this.validateFileExtension(file = file) // 파일 확장자 검증
        }
    }
}