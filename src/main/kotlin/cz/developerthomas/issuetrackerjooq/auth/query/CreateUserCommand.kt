package cz.developerthomas.issuetrackerjooq.auth.query

import cz.developerthomas.issuetrackerjooq.auth.domain.CreateUser
import cz.developerthomas.issuetrackerjooq.tables.records.AppUserRecord
import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class CreateUserCommand(
    private val dsl: DSLContext,
) {

    operator fun invoke(
        command: CreateUser,
    ): UserId = dsl.insertInto(APP_USER)
        .set(command.toUserRecord())
        .returningResult(APP_USER.ID)
        .fetchSingle(APP_USER.ID)
        .let { requireNotNull(it) }

    private fun CreateUser.toUserRecord() = AppUserRecord(
        id = id,
        email = email,
        name = name,
        jobTitle = jobTitle,
        authId = authId,
    )
}