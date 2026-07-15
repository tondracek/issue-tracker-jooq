package cz.developerthomas.issuetrackerjooq.taskcomment.query

import cz.developerthomas.issuetrackerjooq.tables.TaskComment.Companion.TASK_COMMENT
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.view.TaskCommentDetailView
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class GetTaskCommentsQuery(
    private val dsl: DSLContext,
) {

    operator fun invoke(taskId: TaskId): List<TaskCommentDetailView> =
        dsl.selectFrom(TASK_COMMENT)
            .where(TASK_COMMENT.TASK_ID.eq(taskId.value))
            .fetchInto(TaskCommentDetailView::class.java) // TODO: no author
}