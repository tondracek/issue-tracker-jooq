package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.api.exception.TaskNotFoundException
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.view.TaskDetailView
import cz.developerthomas.issuetrackerjooq.taskcomment.query.GetTaskCommentsQuery
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import cz.developerthomas.issuetrackerjooq.user.view.UserPreview
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class GetTaskDetailQuery(
    private val dsl: DSLContext,
    private val getTaskComments: GetTaskCommentsQuery,
) {

    operator fun invoke(id: TaskId): TaskDetailView {
        val task = fetchTaskWithUsers(id)
        val comments = getTaskComments(id)

        return task.copy(comments = comments)
    }

    private fun fetchTaskWithUsers(id: TaskId): TaskDetailView {
        val assignee = APP_USER.`as`("assignee")
        val reporter = APP_USER.`as`("reporter")

        return dsl.select(
            *TASK.fields(),
            *assignee.fields(),
            *reporter.fields(),
        )
            .from(TASK)
            .leftJoin(assignee).on(TASK.ASSIGNEE_ID.eq(assignee.ID))
            .join(reporter).on(TASK.REPORTER_ID.eq(reporter.ID))
            .where(TASK.ID.eq(id.value))
            .fetchOne {
                TaskDetailView(
                    id = TaskId(it.get(TASK.ID)!!),
                    title = it.get(TASK.TITLE)!!,
                    description = it.get(TASK.DESCRIPTION),
                    status = it.get(TASK.STATUS),
                    priority = it.get(TASK.PRIORITY)!!,
                    createdAt = it.get(TASK.CREATED_AT),
                    updatedAt = it.get(TASK.UPDATED_AT),
                    assignee = when {
                        it.get(assignee.ID) == null || it.get(assignee.NAME) == null -> null
                        else -> UserPreview(
                            id = UserId(it.get(assignee.ID)!!),
                            name = it.get(assignee.NAME)!!,
                            jobTitle = it.get(assignee.JOB_TITLE),
                        )
                    },
                    reporter = UserPreview(
                        id = UserId(it.get(reporter.ID)!!),
                        name = it.get(reporter.NAME)!!,
                        jobTitle = it.get(reporter.JOB_TITLE),
                    ),
                    comments = emptyList()
                )
            }
            ?: throw TaskNotFoundException(id)
    }
}

