package cz.developerthomas.issuetrackerjooq.auth.query

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId
import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import cz.developerthomas.issuetrackerjooq.user.exception.UserNotFoundException
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class GetUserIdByAuthIdQuery(
    private val dsl: DSLContext,
) {
    operator fun invoke(authId: AuthId): UserId =
        dsl.select(APP_USER.ID)
            .from(APP_USER)
            .where(APP_USER.AUTH_ID.eq(authId))
            .fetchSingle(APP_USER.ID)
            ?: throw UserNotFoundException(authId)
}