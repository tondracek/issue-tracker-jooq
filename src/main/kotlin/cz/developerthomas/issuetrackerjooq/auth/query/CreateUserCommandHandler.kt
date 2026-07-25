package cz.developerthomas.issuetrackerjooq.auth.query

import cz.developerthomas.issuetrackerjooq.auth.domain.CreateUserCommand
import cz.developerthomas.issuetrackerjooq.tables.records.AppUserRecord
import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class CreateUserCommandHandler(
    private val dsl: DSLContext,
) {

    operator fun invoke(
        command: CreateUserCommand,
    ): UserId = dsl.insertInto(APP_USER)
        .set(command.toUserRecord())
        .returningResult(APP_USER.ID)
        .fetchSingle(APP_USER.ID)
        .let { requireNotNull(it) }

    private fun CreateUserCommand.toUserRecord() = AppUserRecord(
        id = id,
        email = email,
        name = name,
        jobTitle = jobTitle,
        authId = authId,
    )
}