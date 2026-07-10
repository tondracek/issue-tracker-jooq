package cz.developerthomas.issuetrackerjooq.core.exception

import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handle(ex: AppException): ProblemDetail {
        val problem = ProblemDetail.forStatusAndDetail(
            ex.status,
            ex.message
        )

        problem.setProperty("code", ex.code.name)

        return problem
    }
}