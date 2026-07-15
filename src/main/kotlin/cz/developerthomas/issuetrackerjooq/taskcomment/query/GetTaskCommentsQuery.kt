package cz.developerthomas.issuetrackerjooq.taskcomment.query

import cz.developerthomas.issuetrackerjooq.tables.TaskComment.Companion.TASK_COMMENT
import cz.developerthomas.issuetrackerjooq.tables.references.USER_PREVIEW
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.TaskCommentId
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
            userPreviewRow(USER_PREVIEW),
            TASK_COMMENT.CREATED_AT,
        )
            .from(TASK_COMMENT)
            .join(USER_PREVIEW).on(TASK_COMMENT.AUTHOR_ID.eq(USER_PREVIEW.ID))
            .where(TASK_COMMENT.TASK_ID.eq(taskId.value))
            .fetch(
                Records.mapping { id, content, author, createdAt ->
                    TaskCommentDetailView(
                        id = TaskCommentId(id!!),
                        content = content!!,
                        author = author,
                        createdAt = createdAt,
                    )
                }
            )
}