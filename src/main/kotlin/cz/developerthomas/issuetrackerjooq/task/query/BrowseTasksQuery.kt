package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.domain.OrderDirection
import cz.developerthomas.issuetrackerjooq.task.domain.TaskBrowseFilter
import cz.developerthomas.issuetrackerjooq.task.domain.TaskOrdering
import cz.developerthomas.issuetrackerjooq.task.view.TaskListItemView
import cz.developerthomas.issuetrackerjooq.user.view.userPreviewRowNullable
import org.jooq.*
import org.jooq.impl.DSL.or
import org.jooq.impl.DSL.trueCondition
import org.springframework.stereotype.Repository

@Repository
class BrowseTasksQuery(
    private val dsl: DSLContext,
) {

    operator fun invoke(filter: TaskBrowseFilter): List<TaskListItemView> =
        dsl.select(
            TASK.ID,
            TASK.TITLE,
            userPreviewRowNullable(APP_USER),
            TASK.STATUS,
            TASK.PRIORITY,
            TASK.CREATED_AT,
            TASK.UPDATED_AT,
        )
            .from(TASK)
            .leftJoin(APP_USER).on(TASK.ASSIGNEE_ID.eq(APP_USER.ID))
            .where(
                getTextCondition(filter.text),
                TASK.ASSIGNEE_ID.inOptional(filter.assignee),
                TASK.STATUS.inOptional(filter.status),
                TASK.PRIORITY.inOptional(filter.priority),
            )
            .orderBy(
                getOrderingField(filter).applyOrderDirection(filter.orderDirection),
                TASK.TITLE.applyOrderDirection(filter.orderDirection)
            )
            .limitOffsetOptional(filter.limit, filter.offset)
            .fetch(Records.mapping(::TaskListItemView))
}

// ----------
// CONDITIONS
// ----------

private fun getTextCondition(text: String?) = when {
    text.isNullOrEmpty() -> trueCondition()
    else -> or(
        TASK.TITLE.containsIgnoreCase(text),
        TASK.DESCRIPTION.containsIgnoreCase(text),
    )
}

private fun <T> Field<T?>.inOptional(values: List<T>?) = when {
    values.isNullOrEmpty() -> trueCondition()
    else -> this.`in`(values)
}

private fun getOrderingField(filter: TaskBrowseFilter) = when (filter.orderBy) {
    TaskOrdering.ASSIGNEE -> APP_USER.NAME
    TaskOrdering.STATUS -> TASK.STATUS
    TaskOrdering.PRIORITY -> TASK.PRIORITY
    TaskOrdering.CREATED_AT -> TASK.CREATED_AT
    TaskOrdering.UPDATED_AT -> TASK.UPDATED_AT
    null -> TASK.TITLE
}

private fun <T> Field<T>.applyOrderDirection(direction: OrderDirection?) =
    when (direction) {
        OrderDirection.DESCENDING -> this.desc()
        OrderDirection.ASCENDING -> this.asc()
        null -> this.asc()
    }

private fun <R : Record> SelectLimitStep<R>.limitOffsetOptional(
    limit: Int?,
    offset: Int?,
) = when {
    limit == null && offset == null -> this
    limit == null && offset != null -> offset(offset)
    limit != null && offset == null -> limit(limit)
    else -> limit(offset, limit)
}
