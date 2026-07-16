package cz.developerthomas.issuetrackerjooq.user.domain

import cz.developerthomas.issuetrackerjooq.core.validation.parseUUID
import java.util.*

@JvmInline
value class UserId(val value: UUID) {
    companion object {
        fun fromString(uuid: String): UserId =
            UserId(uuid.parseUUID())
    }
}