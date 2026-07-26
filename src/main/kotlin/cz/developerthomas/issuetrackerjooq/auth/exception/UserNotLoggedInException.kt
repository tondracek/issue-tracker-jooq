package cz.developerthomas.issuetrackerjooq.auth.exception

import cz.developerthomas.issuetrackerjooq.core.exception.AppException
import cz.developerthomas.issuetrackerjooq.core.exception.ErrorCode
import org.springframework.http.HttpStatus

class UserNotLoggedInException : AppException(
    status = HttpStatus.UNAUTHORIZED,
    code = ErrorCode.NOT_LOGGED_IN,
    message = "You must first log in",
)
