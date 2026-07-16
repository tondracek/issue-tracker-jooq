package cz.developerthomas.issuetrackerjooq.core.jooq

import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.TaskCommentId
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.Converter
import java.util.*

open class InlineValueConverter<T : Any>(
    private val from: (UUID) -> T,
    private val to: (T) -> UUID,
) : Converter<UUID, T> {

    override fun from(databaseObject: UUID?): T? =
        databaseObject?.let(from)

    override fun to(userObject: T?): UUID? =
        userObject?.let(to)

    override fun fromType() = UUID::class.java

    @Suppress("UNCHECKED_CAST")
    override fun toType() = Any::class.java as Class<T>
}

class TaskIdConverter : InlineValueConverter<TaskId>(
    ::TaskId,
    TaskId::value,
)

class UserIdConverter : InlineValueConverter<UserId>(
    ::UserId,
    UserId::value,
)

class TaskCommentIdConverter : InlineValueConverter<TaskCommentId>(
    ::TaskCommentId,
    TaskCommentId::value,
)