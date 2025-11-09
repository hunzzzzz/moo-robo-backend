package com.moira.moorobo.domain.question.service

import com.moira.moorobo.domain.answer.service.AnswerService
import com.moira.moorobo.domain.question.dto.request.QuestionAddRequest
import com.moira.moorobo.domain.question.dto.request.QuestionUpdateRequest
import com.moira.moorobo.domain.question.dto.response.QuestionDetailResponse
import com.moira.moorobo.domain.question.dto.response.QuestionResponse
import com.moira.moorobo.domain.question.repository.QuestionFileRepository
import com.moira.moorobo.domain.question.repository.QuestionRepository
import com.moira.moorobo.global.dto.SimpleUserAuth
import com.moira.moorobo.global.exception.ErrorCode
import com.moira.moorobo.global.exception.MooRoboException
import com.moira.moorobo.global.utility.CookieHandler
import com.moira.moorobo.global.utility.EntityFinder
import com.moira.moorobo.global.utility.FileValidator
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionService(
    @param:Value("\${spring.profiles.active}")
    private val activeProfile: String,

    private val answerService: AnswerService,
    private val cookieHandler: CookieHandler,
    private val entityFinder: EntityFinder,
    private val fileValidator: FileValidator,
    private val localFileStorageService: LocalFileStorageService,
    private val questionFileRepository: QuestionFileRepository,
    private val questionRepository: QuestionRepository
) {
    @Transactional
    fun add(simpleUserAuth: SimpleUserAuth, request: QuestionAddRequest) {
        val user = entityFinder.findUserById(simpleUserAuth.userId)
        val question = request.toQuestion(user)

        // [1] 첨부파일 검증
        if (!request.files.isEmpty()) {
            fileValidator.validateFiles(files = request.files)
        }

        // [2] Question 객체 저장
        val savedQuestion = questionRepository.save(question)

        // [3] 파일 저장
        request.files.map { file ->
            // [3-1] 로컬 환경에서는 로컬 파일 환경에 저장
            if ("local".equals(activeProfile, ignoreCase = true)) {
                val fileInfo = localFileStorageService.saveFile(file = file, targetDir = "questions")
                val questionFile = fileInfo.toQuestionFile(question = savedQuestion)

                questionFileRepository.save(questionFile)
            }
            // [3-2] 배포 환경에서는 AWS S3에 저장
            // TODO
        }
    }

    @Transactional(readOnly = true)
    fun getMyQuestions(simpleUserAuth: SimpleUserAuth): List<QuestionResponse> {
        return questionRepository.findAllQuestionsByUserId(simpleUserAuth.userId)
    }

    @Transactional
    fun getQuestion(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        questionId: Long
    ): QuestionDetailResponse {
        // [1] questionId 쿠키가 존재하지 않으면 조회수 1 증가 및 쿠키 추가
        if (!cookieHandler.hasQuestionIdInCookie(request = httpServletRequest, questionId = questionId)) {
            val question = entityFinder.findQuestionById(questionId)
            question.viewCount += 1

            cookieHandler.putQuestionIdInCookie(response = httpServletResponse, questionId = questionId)
        }

        // [2] 게시글 정보 조회
        val question = questionRepository.findQuestionById(questionId)
            ?: throw MooRoboException(ErrorCode.QUESTION_NOT_FOUND)

        // [3] 댓글 목록 조회
        val answers = answerService.getAnswers(questionId = questionId)

        // [4] 파일 정보 조회
        val files = questionFileRepository.findAllQuestionFileByQuestionId(questionId = questionId)

        // [5] 게시글 + 댓글 + 파일
        return QuestionDetailResponse(question = question, answers = answers, files = files)
    }

    @Transactional
    fun updateQuestion(
        simpleUserAuth: SimpleUserAuth,
        request: QuestionUpdateRequest,
        questionId: Long
    ) {
        val question = entityFinder.findQuestionById(questionId)

        // [1] 권한 확인
        if (question.user.id != simpleUserAuth.userId) {
            throw MooRoboException(ErrorCode.NOT_YOUR_QUESTION)
        }

        // [2] 수정
        question.title = request.title
        question.content = request.content
        question.aiAnswer = request.aiAnswer
    }

    @Transactional
    fun deleteQuestion(simpleUserAuth: SimpleUserAuth, questionId: Long) {
        val question = entityFinder.findQuestionById(questionId)

        // [1] 권한 확인
        if (question.user.id != simpleUserAuth.userId) {
            throw MooRoboException(ErrorCode.NOT_YOUR_QUESTION)
        }

        // [2] 삭제
        questionRepository.delete(question)
    }
}