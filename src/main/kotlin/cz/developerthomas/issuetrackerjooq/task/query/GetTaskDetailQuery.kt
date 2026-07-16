package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.exception.TaskNotFoundException
import cz.developerthomas.issuetrackerjooq.task.view.TaskDetailView
import cz.developerthomas.issuetrackerjooq.taskcomment.query.GetTaskCommentsQuery
import cz.developerthomas.issuetrackerjooq.user.view.userPreviewRow
import cz.developerthomas.issuetrackerjooq.user.view.userPreviewRowNullable
import org.jooq.DSLContext
import org.jooq.Records
import org.springframework.stereotype.Repository

@Repository
class GetTaskDetailQuery(
    private val dsl: DSLContext,
    private val getTaskComments: GetTaskCommentsQuery,
) {

    operator fun invoke(id: TaskId): TaskDetailView {
        val taskWithUsers = fetchTaskWithUsers(id)
        val comments = getTaskComments(id)

        return taskWithUsers.copy(comments = comments)
    }

    private fun fetchTaskWithUsers(id: TaskId): TaskDetailView {
        val assignee = APP_USER.`as`("assignee")
        val reporter = APP_USER.`as`("reporter")

        return dsl.select(
            TASK.ID,
            TASK.TITLE,
            TASK.DESCRIPTION,
            userPreviewRowNullable(assignee),
            userPreviewRow(reporter),
            TASK.STATUS,
            TASK.PRIORITY,
            TASK.CREATED_AT,
            TASK.UPDATED_AT,
        )
            .from(TASK)
            .leftJoin(assignee).on(TASK.ASSIGNEE_ID.eq(assignee.ID))
            .join(reporter).on(TASK.REPORTER_ID.eq(reporter.ID))
            .where(TASK.ID.eq(id))
            .fetchOne(Records.mapping(TaskDetailView::from))
            ?: throw TaskNotFoundException(id)
    }
}
