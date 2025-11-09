package com.moira.moorobo.global.file

import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import com.moira.moorobo.global.file.dto.FileDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID

@Service
class LocalFileStorageHandler(
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

    fun saveFile(file: MultipartFile, targetDir: String): FileDto {
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
            log.info("파일 저장 성공: {}", targetPath)

            // [4] DB 저장을 위한 DTO 반환
            return FileDto(
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

    fun loadFileAsResource(fileUrl: String): Resource {
        try {
            // [1] DB에 저장된 파일 경로를 Path로 변환
            val filePath = Paths.get(fileUrl).normalize()

            // [2] Path를 URI로 변환하여 UrlResource 객체 생성
            val resource = UrlResource(filePath.toUri())

            // [3] Resource 유효성 검사 후 리턴
            if (resource.exists() && resource.isReadable) {
                return resource
            } else {
                log.error("파일을 찾을 수 없거나 읽을 수 없습니다: {}", fileUrl)
                throw MooRoboException(ErrorCode.FILE_NOT_FOUND)
            }
        } catch (e: Exception) {
            log.error("파일 로드 실패: {}", e.message, e)
            throw MooRoboException(ErrorCode.FILE_LOAD_FAILED)
        }
    }

    fun deleteFile(fileUrl: String) {
        try {
            // [1] DB에 저장된 파일 경로를 Path로 변환
            val filePath = Paths.get(fileUrl).normalize()

            // [2] 파일이 설정된 업로드 디렉터리 내에 있는지 확인
            // fileUrl에 경로 조작을 넣어 서버의 민감한 파일을 삭제하는 것을 방지
            if (!filePath.startsWith(this.fileStorageLocation)) {
                log.warn("보안 위험: 삭제 요청 경로가 지정된 업로드 위치를 벗어납니다. {}", fileUrl)
                throw MooRoboException(ErrorCode.FILE_DELETE_FORBIDDEN)
            }

            // [3] 파일 삭제
            val isDeleted = Files.deleteIfExists(filePath)

            if (isDeleted) {
                log.info("파일 삭제 성공: {}", filePath)
            } else {
                log.error("파일 삭제 실패. 파일이 존재하지 않습니다: {}", filePath)
            }
        } catch (e: Exception) {
            log.error("파일 로드 실패: {}", e.message, e)
            throw MooRoboException(ErrorCode.FILE_DELETE_FAILED)
        }
    }
}