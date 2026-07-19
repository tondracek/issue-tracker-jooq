package cz.developerthomas.issuetrackerjooq.core.helper

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.tables.records.TaskRecord
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.DSLContext
import java.util.*

fun DSLContext.insertTask(
    id: TaskId = TaskId(UUID.randomUUID()),
    title: String = "Task Title",
    description: String? = "Task description",
    assigneeId: UserId? = insertUser().id,
    reporterId: UserId = insertUser().id,
    status: TaskStatus = TaskStatus.TODO,
    priority: TaskPriority = TaskPriority.MEDIUM,
): TaskRecord {
    val task = TaskRecord(
        id = id,
        title = title,
        description = description,
        assigneeId = assigneeId,
        reporterId = reporterId,
        status = status,
        priority = priority,
    )

    return insertInto(TASK)
        .set(task)
        .returning()
        .fetchSingle()
}

