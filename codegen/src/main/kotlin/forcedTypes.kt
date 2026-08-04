import org.jooq.meta.jaxb.ForcedType


private fun columns(vararg columns: String): String = columns.joinToString("|")

val userIdForcedType: ForcedType = ForcedType()
    .withUserType("cz.developerthomas.issuetrackerjooq.user.domain.UserId")
    .withConverter("cz.developerthomas.issuetrackerjooq.core.jooq.UserIdConverter")
    .withIncludeExpression(
        columns(
            "app_user.id",
            "task.assignee_id",
            "task.reporter_id",
            "task_comment.author_id",
            "audit_log.author_id",
        )
    )

val authIdForcedType: ForcedType = ForcedType()
    .withUserType("cz.developerthomas.issuetrackerjooq.auth.domain.AuthId")
    .withConverter("cz.developerthomas.issuetrackerjooq.core.jooq.AuthIdConverter")
    .withIncludeExpression(
        columns(
            "app_user.auth_id",
        )
    )

val taskIdForcedType: ForcedType = ForcedType()
    .withUserType("cz.developerthomas.issuetrackerjooq.task.domain.TaskId")
    .withConverter("cz.developerthomas.issuetrackerjooq.core.jooq.TaskIdConverter")
    .withIncludeExpression(
        columns(
            "task.id",
            "task_comment.task_id",
        )
    )

val taskCommentIdForcedType: ForcedType = ForcedType()
    .withUserType("cz.developerthomas.issuetrackerjooq.taskcomment.domain.TaskCommentId")
    .withConverter("cz.developerthomas.issuetrackerjooq.core.jooq.TaskCommentIdConverter")
    .withIncludeExpression(
        columns(
            "task_comment.id",
        )
    )

val auditLogIdForcedType: ForcedType = ForcedType()
    .withUserType("cz.developerthomas.issuetrackerjooq.audit.domain.AuditLogId")
    .withConverter("cz.developerthomas.issuetrackerjooq.core.jooq.AuditLogIdConverter")
    .withIncludeExpression(
        columns(
            "audit_log.id",
        )
    )