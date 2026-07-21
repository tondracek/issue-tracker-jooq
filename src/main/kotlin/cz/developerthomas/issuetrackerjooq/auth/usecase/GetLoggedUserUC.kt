package cz.developerthomas.issuetrackerjooq.auth.usecase

import cz.developerthomas.issuetrackerjooq.auth.query.GetUserByAuthIdQuery
import cz.developerthomas.issuetrackerjooq.user.domain.CurrentUser
import org.springframework.stereotype.Service

@Service
class GetLoggedUserUC(
    private val getUserByAuthIdQuery: GetUserByAuthIdQuery,
) {

    operator fun invoke(): CurrentUser {
        val authId = TODO()

        return getUserByAuthIdQuery(authId)
    }
}

