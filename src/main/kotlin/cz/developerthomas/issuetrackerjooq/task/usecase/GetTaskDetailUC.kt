package cz.developerthomas.issuetrackerjooq.task.usecase

import cz.developerthomas.issuetrackerjooq.task.query.GetTaskDetailQuery
import cz.developerthomas.issuetrackerjooq.task.view.TaskId
import org.springframework.stereotype.Service

@Service
class GetTaskDetailUC(
    private val getTaskDetailQuery: GetTaskDetailQuery,
) {

    operator fun invoke(id: TaskId) = getTaskDetailQuery(id)
}