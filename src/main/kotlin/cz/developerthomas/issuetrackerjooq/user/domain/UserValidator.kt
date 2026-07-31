package cz.developerthomas.issuetrackerjooq.user.domain

import cz.developerthomas.issuetrackerjooq.core.exception.AppException
import cz.developerthomas.issuetrackerjooq.user.exception.UserNotFoundException
import cz.developerthomas.issuetrackerjooq.user.query.UserExistsQuery
import org.springframework.stereotype.Service

@Service
class UserValidator(
    private val userExistsQuery: UserExistsQuery,
) {

    fun requireExists(
        userId: UserId,
        onNotFound: () -> AppException = { UserNotFoundException(userId) }
    ) {
        if (!userExistsQuery(userId)) throw onNotFound()
    }
}