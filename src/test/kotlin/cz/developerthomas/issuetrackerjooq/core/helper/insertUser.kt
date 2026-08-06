package cz.developerthomas.issuetrackerjooq.core.helper

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId
import cz.developerthomas.issuetrackerjooq.tables.records.AppUserRecord
import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.DSLContext
import java.util.*

fun DSLContext.insertUser(
    id: UserId = UserId(UUID.randomUUID()),
    email: String = "${id}@mail.com",
    name: String = "User Name",
    jobTitle: String = "Tester",
): AppUserRecord {
    val user = AppUserRecord(
        id = id,
        authId = AuthId(id.value.toString()),
        email = email,
        name = name,
        jobTitle = jobTitle
    )

    return insertInto(APP_USER)
        .set(user)
        .returning()
        .fetchSingle()
}