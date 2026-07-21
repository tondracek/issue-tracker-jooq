package cz.developerthomas.issuetrackerjooq.auth.query

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId
import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.user.domain.CurrentUser
import cz.developerthomas.issuetrackerjooq.user.exception.UserNotFoundException
import org.jooq.DSLContext
import org.jooq.Records
import org.springframework.stereotype.Repository

@Repository
class GetUserByAuthIdQuery(
    private val dsl: DSLContext,
) {
    operator fun invoke(authId: AuthId): CurrentUser =
        dsl.select(
            APP_USER.ID,
            APP_USER.AUTH_ID,
        )
            .from(APP_USER)
            .where(APP_USER.AUTH_ID.eq(authId))
            .fetchOne(Records.mapping(::CurrentUser))
            ?: throw UserNotFoundException(authId.toString())
}