package com.moira.moorobo.domain.question.service

import com.moira.moorobo.global.dto.QuestionFileDto
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class LocalFileStorageService(
    @param:Value("\${file.upload-dir}")
    private val fileUploadDir: String
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    // 절대 경로 (ex. C:\workspace\moo-robo\upload)
    private val fileStorageLocation: Path = Paths.get(fileUploadDir).toAbsolutePath().normalize()

    // Bean 생성 후 초기화 시점에 파일 저장 디렉터리를 생성
    init {
        try {
            Files.createDirectories(this.fileStorageLocation)
            log.info("로컬 파일 저장 디렉터리 생성 완료: {}", this.fileStorageLocation)
        } catch (e: Exception) {
            log.error("파일 저장 디렉터리 생성 실패: {}", fileStorageLocation, e)
            throw MooRoboException(ErrorCode.LOCAL_FILE_SYSTEM_ERROR)
        }
    }

    fun saveFile(file: MultipartFile, targetDir: String): QuestionFileDto {
        // 원본 파일명 (사용자가 지정한 파일명)
        val originalFileName = file.originalFilename ?: throw MooRoboException(ErrorCode.INVALID_FILE)
        // 파일 확장자
        val fileExtension = originalFileName.substringAfterLast(".", "")
        // 고유 ID
        val fileId = UUID.randomUUID().toString()
        // 파일 시스템에 저장되는 파일명
        val storedFileName = "${fileId}.$fileExtension"
        // 하위 경로를 포함한 최종 디렉터리 (ex. C:\workspace\moo-robo\upload\questions)
        val targetDirectory = fileStorageLocation.resolve(targetDir)

        println("========== 파일 정보 ==========")
        println(
            """
            originalFileName: $originalFileName
            fileExtension   : $fileExtension
            storedFileName  : $storedFileName
            targetDirectory : $targetDirectory
        """.trimIndent()
        )

        try {
            // [1] 파일 저장 하위 디렉터리 생성
            Files.createDirectories(targetDirectory)

            // [2] 최종 디렉터리를 Path로 변환
            val targetPath = targetDirectory.resolve(storedFileName).normalize()

            // [3] 파일 시스템에 저장
            file.transferTo(targetPath.toFile())

            // [4] DB 저장을 위한 DTO 반환
            return QuestionFileDto(
                fileId = fileId,
                size = file.size,
                originalFileName = originalFileName,
                storedFileName = storedFileName,
                fileUrl = targetPath.toString()
            )
        } catch (e: Exception) {
            log.error("파일 저장 실패: {}", e.message, e)
            throw MooRoboException(ErrorCode.FILE_SAVE_FAILED)
        }
    }
}