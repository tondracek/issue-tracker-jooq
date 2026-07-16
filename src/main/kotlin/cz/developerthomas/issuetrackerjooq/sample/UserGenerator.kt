package cz.developerthomas.issuetrackerjooq.sample

import cz.developerthomas.issuetrackerjooq.tables.records.AppUserRecord
import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class UserGenerator(
    private val dsl: DSLContext,
) {

    companion object {
        val SOFTWARE_ENGINEER = AppUserRecord(
            id = UserId.fromString("38aedc70-d562-4148-90be-a2fae8684eca"),
            email = "jan.novak@gmail.com",
            name = "Jan Novák",
            jobTitle = "Software Engineer",
        )

        val PRODUCT_MANAGER = AppUserRecord(
            id = UserId.fromString("38aedc70-d562-4148-90be-a2fae8684ecb"),
            email = "john.doe@gmail.com",
            name = "John Doe",
            jobTitle = "Product Manager",
        )
    }

    fun generate() {
        dsl.upsertSample(SOFTWARE_ENGINEER, APP_USER, APP_USER.ID)
        dsl.upsertSample(PRODUCT_MANAGER, APP_USER, APP_USER.ID)
    }
}