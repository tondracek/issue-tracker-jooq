package cz.developerthomas.issuetrackerjooq.task.domain.status

import cz.developerthomas.issuetrackerjooq.enums.AppRole
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.Task
import cz.developerthomas.issuetrackerjooq.user.domain.CurrentUser

sealed interface StatusTransitionResult {
    data object Allowed : StatusTransitionResult
    data object Forbidden : StatusTransitionResult
    data object Invalid : StatusTransitionResult
}

internal fun evaluateStatusTransition(
    currentUser: CurrentUser,
    task: Task,
    to: TaskStatus,
): StatusTransitionResult {
    if (currentUser.role == AppRole.ADMIN)
        return StatusTransitionResult.Allowed

    val from = task.status

    return when (from) {
        TaskStatus.TODO -> when (to) {
            TaskStatus.TODO -> StatusTransitionResult.Allowed
            TaskStatus.IN_PROGRESS -> checkIsAssigneeOrReporter(task, currentUser)
            TaskStatus.DONE -> checkIsAssigneeOrReporter(task, currentUser)
            TaskStatus.CANCELLED -> checkIsReporter(task, currentUser)
            TaskStatus.CLOSED -> StatusTransitionResult.Invalid
        }

        TaskStatus.IN_PROGRESS -> when (to) {
            TaskStatus.TODO -> checkIsAssigneeOrReporter(task, currentUser)
            TaskStatus.IN_PROGRESS -> StatusTransitionResult.Allowed
            TaskStatus.DONE -> checkIsAssigneeOrReporter(task, currentUser)
            TaskStatus.CANCELLED -> checkIsReporter(task, currentUser)
            TaskStatus.CLOSED -> StatusTransitionResult.Invalid
        }

        TaskStatus.DONE -> when (to) {
            TaskStatus.TODO -> StatusTransitionResult.Invalid
            TaskStatus.IN_PROGRESS -> checkIsAssigneeOrReporter(task, currentUser)
            TaskStatus.DONE -> StatusTransitionResult.Allowed
            TaskStatus.CANCELLED -> StatusTransitionResult.Invalid
            TaskStatus.CLOSED -> checkIsReporter(task, currentUser)
        }

        TaskStatus.CANCELLED -> when (to) {
            TaskStatus.TODO -> checkIsReporter(task, currentUser)
            TaskStatus.IN_PROGRESS -> checkIsReporter(task, currentUser)
            TaskStatus.DONE -> StatusTransitionResult.Invalid
            TaskStatus.CANCELLED -> StatusTransitionResult.Allowed
            TaskStatus.CLOSED -> StatusTransitionResult.Invalid
        }

        TaskStatus.CLOSED -> when (to) {
            TaskStatus.TODO -> StatusTransitionResult.Invalid
            TaskStatus.IN_PROGRESS -> StatusTransitionResult.Invalid
            TaskStatus.DONE -> checkIsReporter(task, currentUser)
            TaskStatus.CANCELLED -> StatusTransitionResult.Invalid
            TaskStatus.CLOSED -> StatusTransitionResult.Allowed
        }
    }
}

private fun checkIsAssigneeOrReporter(task: Task, currentUser: CurrentUser) = when (currentUser.id) {
    task.assigneeId, task.reporterId -> StatusTransitionResult.Allowed
    else -> StatusTransitionResult.Forbidden
}

private fun checkIsReporter(task: Task, currentUser: CurrentUser) = when (currentUser.id) {
    task.reporterId -> StatusTransitionResult.Allowed
    else -> StatusTransitionResult.Forbidden
}
