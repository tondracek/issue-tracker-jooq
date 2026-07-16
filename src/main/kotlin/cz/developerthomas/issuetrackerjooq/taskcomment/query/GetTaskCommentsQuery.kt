package cz.developerthomas.issuetrackerjooq.taskcomment.query

import cz.developerthomas.issuetrackerjooq.tables.TaskComment.Companion.TASK_COMMENT
import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.view.TaskCommentDetailView
import cz.developerthomas.issuetrackerjooq.user.view.userPreviewRow
import org.jooq.DSLContext
import org.jooq.Records
import org.springframework.stereotype.Repository

@Repository
class GetTaskCommentsQuery(
    private val dsl: DSLContext,
) {

    operator fun invoke(taskId: TaskId): List<TaskCommentDetailView> =
        dsl.select(
            TASK_COMMENT.ID,
            TASK_COMMENT.CONTENT,
            userPreviewRow(APP_USER),
            TASK_COMMENT.CREATED_AT,
        )
            .from(TASK_COMMENT)
            .join(APP_USER).on(TASK_COMMENT.AUTHOR_ID.eq(APP_USER.ID))
            .where(TASK_COMMENT.TASK_ID.eq(taskId))
            .fetch(Records.mapping(::TaskCommentDetailView))
}