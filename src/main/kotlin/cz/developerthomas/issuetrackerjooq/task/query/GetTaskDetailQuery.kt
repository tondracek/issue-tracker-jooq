package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.pojos.TaskWithUsers
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.tables.references.TASK_WITH_USERS
import cz.developerthomas.issuetrackerjooq.task.api.exception.TaskNotFoundException
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.view.TaskDetailView
import cz.developerthomas.issuetrackerjooq.taskcomment.query.GetTaskCommentsQuery
import cz.developerthomas.issuetrackerjooq.taskcomment.view.TaskCommentDetailView
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import cz.developerthomas.issuetrackerjooq.user.view.UserPreview
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class GetTaskDetailQuery(
    private val dsl: DSLContext,
    private val getTaskComments: GetTaskCommentsQuery,
) {

    operator fun invoke(id: TaskId): TaskDetailView {
        val taskWithUsers = fetchTaskWithUsers(id)
        val comments = getTaskComments(id)

        return taskWithUsers.toTaskDetailView(comments)
    }

    private fun fetchTaskWithUsers(id: TaskId): TaskWithUsers =
        dsl.selectFrom(TASK_WITH_USERS)
            .where(TASK.ID.eq(id.value))
            .fetchOneInto(TaskWithUsers::class.java)
            ?: throw TaskNotFoundException(id)
}

private fun TaskWithUsers.toTaskDetailView(comments: List<TaskCommentDetailView>) = TaskDetailView(
    id = TaskId(id!!),
    title = title!!,
    description = description,
    assignee = when {
        assigneeId == null || assigneeName == null -> null
        else -> toUserPreview(id = assigneeId, name = assigneeName, jobTitle = assigneeJobTitle)
    },
    reporter = toUserPreview(id = reporterId!!, name = reporterName!!, jobTitle = reporterJobTitle),
    status = status!!,
    priority = priority!!,
    createdAt = createdAt!!,
    updatedAt = updatedAt!!,
    comments = comments,
)

private fun toUserPreview(
    id: UUID,
    name: String,
    jobTitle: String?
) = UserPreview(
    id = UserId(id),
    name = name,
    jobTitle = jobTitle,
)