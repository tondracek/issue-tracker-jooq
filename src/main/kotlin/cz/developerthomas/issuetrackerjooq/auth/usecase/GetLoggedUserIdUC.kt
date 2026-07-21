package cz.developerthomas.issuetrackerjooq.auth.usecase

import cz.developerthomas.issuetrackerjooq.auth.query.GetUserIdByAuthIdQuery
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.springframework.stereotype.Service

@Service
class GetLoggedUserIdUC(
    getUserIdByAuthIdQuery: GetUserIdByAuthIdQuery,
) {

    operator fun invoke(): UserId {

    }
}

