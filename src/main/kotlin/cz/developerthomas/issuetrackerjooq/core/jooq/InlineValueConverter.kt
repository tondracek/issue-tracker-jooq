package cz.developerthomas.issuetrackerjooq.core.jooq

import cz.developerthomas.issuetrackerjooq.audit.domain.AuditLogId
import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.TaskCommentId
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.Converter
import java.util.*

open class InlineValueConverter<T : Any, ID>(
    private val from: (ID) -> T,
    private val to: (T) -> ID,
    private val idClass: Class<ID>,
) : Converter<ID, T> {

    override fun from(databaseObject: ID?): T? =
        databaseObject?.let(from)

    override fun to(userObject: T?): ID? =
        userObject?.let(to)

    override fun fromType() = idClass

    @Suppress("UNCHECKED_CAST")
    override fun toType() = Any::class.java as Class<T>
}

class TaskIdConverter : InlineValueConverter<TaskId, UUID>(
    ::TaskId,
    TaskId::value,
    UUID::class.java
)

class UserIdConverter : InlineValueConverter<UserId, UUID>(
    ::UserId,
    UserId::value,
    UUID::class.java
)

class AuthIdConverter : InlineValueConverter<AuthId, String>(
    ::AuthId,
    AuthId::value,
    String::class.java
)

class TaskCommentIdConverter : InlineValueConverter<TaskCommentId, UUID>(
    ::TaskCommentId,
    TaskCommentId::value,
    UUID::class.java
)

class AuditLogIdConverter : InlineValueConverter<AuditLogId, UUID>(
    ::AuditLogId,
    AuditLogId::value,
    UUID::class.java
)