package cz.developerthomas.issuetrackerjooq.sample

import cz.developerthomas.issuetrackerjooq.tables.records.AppUserRecord
import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserGenerator(
    private val dsl: DSLContext
) {

    private val sampleUser = AppUserRecord(
        id = UUID.fromString("38aedc70-d562-4148-90be-a2fae8684eca"),
        email = "jan.novak@gmail.com",
        name = "Jan Novák",
        jobTitle = "Software Engineer",
    )

    fun generate() {
        dsl.insertInto(APP_USER)
            .set(sampleUser)
            .onConflict(APP_USER.ID).doUpdate()
            .set(sampleUser)
            .execute()
    }
}