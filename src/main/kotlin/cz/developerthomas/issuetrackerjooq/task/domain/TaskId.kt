package cz.developerthomas.issuetrackerjooq.task.domain

import cz.developerthomas.issuetrackerjooq.core.validation.parseUUID
import java.util.*

@JvmInline
value class TaskId(val value: UUID) {

    companion object {
        fun fromString(uuid: String) =
            TaskId(uuid.parseUUID())
    }
}


fun String.parseTaskId(): TaskId = TaskId(parseUUID())

