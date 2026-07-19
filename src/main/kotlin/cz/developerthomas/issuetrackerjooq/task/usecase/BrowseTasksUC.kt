package cz.developerthomas.issuetrackerjooq.task.usecase

import cz.developerthomas.issuetrackerjooq.task.domain.TaskBrowseFilter
import cz.developerthomas.issuetrackerjooq.task.query.BrowseTasksQuery
import cz.developerthomas.issuetrackerjooq.task.view.TaskListItemView
import org.springframework.stereotype.Service

@Service
class BrowseTasksUC(
    private val browseTasksQuery: BrowseTasksQuery,
) {
    operator fun invoke(filter: TaskBrowseFilter): List<TaskListItemView> {
        return browseTasksQuery(filter)
    }
}