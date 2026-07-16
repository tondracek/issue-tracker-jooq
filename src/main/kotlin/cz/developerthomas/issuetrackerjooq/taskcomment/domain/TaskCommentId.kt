package cz.developerthomas.issuetrackerjooq.taskcomment.domain

import cz.developerthomas.issuetrackerjooq.core.validation.parseUUID
import java.util.*

@JvmInline
value class TaskCommentId(val value: UUID) {
    companion object {
        fun fromString(uuid: String) =
            TaskCommentId(uuid.parseUUID())
    }
}