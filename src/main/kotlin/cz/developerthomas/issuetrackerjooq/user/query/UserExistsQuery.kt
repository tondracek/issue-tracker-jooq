package cz.developerthomas.issuetrackerjooq.user.query

import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserExistsQuery(
    private val dsl: DSLContext,
) {
    operator fun invoke(id: UserId): Boolean =
        dsl.fetchExists(
            APP_USER,
            APP_USER.ID.eq(id),
        )
}