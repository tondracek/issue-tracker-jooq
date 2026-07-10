package cz.developerthomas.issuetrackerjooq.task.api.exception

import cz.developerthomas.issuetrackerjooq.core.exception.AppException
import cz.developerthomas.issuetrackerjooq.core.exception.ErrorCode
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import org.springframework.http.HttpStatus

class TaskNotFoundException(id: TaskId) : AppException(
    status = HttpStatus.NOT_FOUND,
    code = ErrorCode.TASK_NOT_FOUND,
    message = "Task with id $id not found",
)
