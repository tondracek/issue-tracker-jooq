package cz.developerthomas.issuetrackerjooq.task.exception

import cz.developerthomas.issuetrackerjooq.core.exception.AppException
import cz.developerthomas.issuetrackerjooq.core.exception.ErrorCode
import org.springframework.http.HttpStatus

class ForbiddenStatusTransitionException : AppException(
    status = HttpStatus.FORBIDDEN,
    code = ErrorCode.FORBIDDEN_STATUS_TRANSITION,
    message = "You are not allowed to perform this status transition.",
)