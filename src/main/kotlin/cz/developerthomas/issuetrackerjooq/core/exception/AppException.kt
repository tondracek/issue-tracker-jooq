package cz.developerthomas.issuetrackerjooq.core.exception

import org.springframework.http.HttpStatusCode

abstract class AppException(
    val status: HttpStatusCode,
    val code: ErrorCode,
    override val message: String,
) : RuntimeException(message)
