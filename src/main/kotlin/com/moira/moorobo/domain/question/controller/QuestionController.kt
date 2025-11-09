package com.moira.moorobo.domain.question.controller

import com.moira.moorobo.domain.question.dto.request.QuestionAddRequest
import com.moira.moorobo.domain.question.dto.request.QuestionUpdateRequest
import com.moira.moorobo.domain.question.dto.response.QuestionDetailResponse
import com.moira.moorobo.domain.question.dto.response.QuestionIdResponse
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.domain.question.service.QuestionService
import com.moira.moorobo.global.auth.UserPrincipal
import com.moira.moorobo.global.auth.dto.SimpleUserAuth
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class QuestionController(
    private val questionService: QuestionService
) {
    @PostMapping("/questions")
    fun addQuestion(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @ModelAttribute request: QuestionAddRequest
    ): ResponseEntity<QuestionIdResponse> {
        val questionIdResponse = questionService.addQuestion(simpleUserAuth, request)

        return ResponseEntity.ok(questionIdResponse)
    }

    @GetMapping("/me/questions")
    fun getMyQuestions(
        @UserPrincipal simpleUserAuth: SimpleUserAuth
    ): ResponseEntity<List<QuestionResponse>> {
        val list = questionService.getMyQuestions(simpleUserAuth)

        return ResponseEntity.ok(list)
    }

    @GetMapping("/questions/{questionId}")
    fun getQuestion(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @PathVariable questionId: Long,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<QuestionDetailResponse> {
        val response = questionService.getQuestion(
            simpleUserAuth = simpleUserAuth,
            questionId = questionId,
            httpServletRequest = httpServletRequest,
            httpServletResponse = httpServletResponse
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/questions/{questionId}/files/{fileId}")
    fun downloadFile(
        @PathVariable questionId: Long,
        @PathVariable fileId: String
    ): ResponseEntity<Resource> {
        val fileDownloadDto = questionService.downloadFile(questionId = questionId, fileId = fileId)

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${fileDownloadDto.encodedFileName}\"")
            .body(fileDownloadDto.resource)
    }

    @PutMapping("/questions/{questionId}")
    fun updateQuestion(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @ModelAttribute request: QuestionUpdateRequest,
        @PathVariable questionId: Long
    ): ResponseEntity<Nothing> {
        questionService.updateQuestion(simpleUserAuth, request, questionId)

        return ResponseEntity.ok(null)
    }

    @DeleteMapping("/questions/{questionId}")
    fun deleteQuestion(
        @UserPrincipal simpleUserAuth: SimpleUserAuth,
        @PathVariable questionId: Long
    ): ResponseEntity<Nothing> {
        questionService.deleteQuestion(simpleUserAuth, questionId)

        return ResponseEntity.ok(null)
    }
}