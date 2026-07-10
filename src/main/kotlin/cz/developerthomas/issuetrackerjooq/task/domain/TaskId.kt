package cz.developerthomas.issuetrackerjooq.task.domain

import cz.developerthomas.issuetrackerjooq.core.exception.InvalidIdException
import java.util.*

@JvmInline
value class TaskId(val value: UUID)

fun String.parseTaskId(): TaskId = TaskId(parseUUID())

fun String.parseUUID(): UUID = runCatching { UUID.fromString(this) }
    .getOrElse { throw InvalidIdException("Invalid UUID: $this") }



