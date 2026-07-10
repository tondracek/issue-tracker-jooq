package cz.developerthomas.issuetrackerjooq.task.usecase

import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.query.GetTaskDetailQuery
import org.springframework.stereotype.Service

@Service
class GetTaskDetailUC(
    private val getTaskDetailQuery: GetTaskDetailQuery,
) {

    operator fun invoke(id: TaskId) = getTaskDetailQuery(id)
}