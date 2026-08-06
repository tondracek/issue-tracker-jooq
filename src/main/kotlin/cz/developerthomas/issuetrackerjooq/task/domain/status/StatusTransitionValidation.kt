package cz.developerthomas.issuetrackerjooq.task.domain.status

import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.Task
import cz.developerthomas.issuetrackerjooq.task.exception.ForbiddenStatusTransitionException
import cz.developerthomas.issuetrackerjooq.task.exception.InvalidStatusTransitionException
import cz.developerthomas.issuetrackerjooq.user.domain.CurrentUser

fun validateStatusTransition(
    currentUser: CurrentUser,
    originalTask: Task,
    to: TaskStatus,
) = when (evaluateStatusTransition(currentUser, originalTask, to)) {
    StatusTransitionResult.Allowed -> Unit
    StatusTransitionResult.Forbidden -> throw ForbiddenStatusTransitionException()
    StatusTransitionResult.Invalid -> throw InvalidStatusTransitionException()
}

fun getAllowedStatusTransitions(
    currentUser: CurrentUser,
    task: Task,
): List<TaskStatus> = TaskStatus.entries.filter { to ->
    evaluateStatusTransition(currentUser, task, to) == StatusTransitionResult.Allowed
}
