package cz.developerthomas.issuetrackerjooq.task.exception

import cz.developerthomas.issuetrackerjooq.core.exception.AppException
import cz.developerthomas.issuetrackerjooq.core.exception.ErrorCode
import org.springframework.http.HttpStatus

class InvalidStatusTransitionException : AppException(
    status = HttpStatus.BAD_REQUEST,
    code = ErrorCode.INVALID_STATUS_TRANSITION,
    message = "Invalid status transition",
)