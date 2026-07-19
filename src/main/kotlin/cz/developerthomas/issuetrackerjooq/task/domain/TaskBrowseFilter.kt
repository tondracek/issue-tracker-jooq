package cz.developerthomas.issuetrackerjooq.task.domain

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.user.domain.UserId

data class TaskBrowseFilter(
    val text: String?,
    val assignee: List<UserId?>?,
    val status: List<TaskStatus>?,
    val priority: List<TaskPriority>?,
    //
    val offset: Int?,
    val limit: Int?,
    //
    val orderBy: TaskOrdering?,
    val orderDirection: OrderDirection?,
)

enum class TaskOrdering {
    ASSIGNEE,
    STATUS,
    PRIORITY,
    CREATED_AT,
    UPDATED_AT,
}

enum class OrderDirection {
    ASCENDING,
    DESCENDING,
}