package cz.developerthomas.issuetrackerjooq.user.domain

import cz.developerthomas.issuetrackerjooq.user.exception.UserNotFoundException
import cz.developerthomas.issuetrackerjooq.user.query.UserExistsQuery
import org.springframework.stereotype.Service

@Service
class UserValidator(
    private val userExistsQuery: UserExistsQuery,
) {

    fun requireExists(userId: UserId) {
        if (!userExistsQuery(userId))
            throw UserNotFoundException(userId)
    }
}