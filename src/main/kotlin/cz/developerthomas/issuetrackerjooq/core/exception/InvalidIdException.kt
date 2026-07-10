package cz.developerthomas.issuetrackerjooq.core.exception

import org.springframework.http.HttpStatus

class InvalidIdException(message: String = "Invalid ID") :
    AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_ID, message)