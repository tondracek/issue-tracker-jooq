package cz.developerthomas.issuetrackerjooq.core.validation

import cz.developerthomas.issuetrackerjooq.core.exception.InvalidIdException
import java.util.*

fun String.parseUUID(): UUID = runCatching { UUID.fromString(this) }
    .getOrElse { throw InvalidIdException("Invalid UUID: $this") }