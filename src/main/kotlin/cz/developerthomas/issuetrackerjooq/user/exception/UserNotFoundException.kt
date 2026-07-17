package cz.developerthomas.issuetrackerjooq.user.exception

import cz.developerthomas.issuetrackerjooq.core.exception.AppException
import cz.developerthomas.issuetrackerjooq.core.exception.ErrorCode
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.springframework.http.HttpStatus

class UserNotFoundException(id: UserId) : AppException(
    status = HttpStatus.NOT_FOUND,
    code = ErrorCode.USER_NOT_FOUND,
    message = "User with id $id not found",
)

