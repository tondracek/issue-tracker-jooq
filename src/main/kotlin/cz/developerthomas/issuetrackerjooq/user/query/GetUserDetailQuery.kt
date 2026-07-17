package cz.developerthomas.issuetrackerjooq.user.query

import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import cz.developerthomas.issuetrackerjooq.user.exception.UserNotFoundException
import cz.developerthomas.issuetrackerjooq.user.view.UserDetailView
import org.jooq.DSLContext
import org.jooq.Records
import org.springframework.stereotype.Repository

@Repository
class GetUserDetailQuery(
    private val dsl: DSLContext,
) {

    operator fun invoke(id: UserId): UserDetailView =
        dsl.select(
            APP_USER.ID,
            APP_USER.EMAIL,
            APP_USER.NAME,
            APP_USER.JOB_TITLE,
            APP_USER.CREATED_AT,
        )
            .from(APP_USER)
            .where(APP_USER.ID.eq(id))
            .fetchOne(Records.mapping(::UserDetailView))
            ?: throw UserNotFoundException(id)
}
