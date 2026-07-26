package cz.developerthomas.issuetrackerjooq.auth.usecase

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId
import cz.developerthomas.issuetrackerjooq.auth.exception.UserNotLoggedInException
import cz.developerthomas.issuetrackerjooq.auth.query.GetUserByAuthIdQuery
import cz.developerthomas.issuetrackerjooq.user.domain.CurrentUser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class GetLoggedUserUC(
    private val getUserByAuthIdQuery: GetUserByAuthIdQuery,
) {

    operator fun invoke(): CurrentUser {
        val jwt = SecurityContextHolder.getContext().authentication?.principal as? Jwt
        val jwtSubject = jwt?.subject ?: throw UserNotLoggedInException()

        val authId = AuthId(jwtSubject)

        return getUserByAuthIdQuery(authId)
    }
}

