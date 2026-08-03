package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.core.fieldupdate.FieldUpdate
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.domain.UpdateTaskCommand
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.TableField
import org.jooq.UpdateQuery
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UpdateTaskCommandHandler(
    private val dsl: DSLContext,
) {

    operator fun invoke(id: TaskId, updateTaskCommand: UpdateTaskCommand) {
        val update = dsl.updateQuery(TASK)

        update.addValueIfSpecified(TASK.TITLE, updateTaskCommand.title)
        update.addValueIfSpecified(TASK.DESCRIPTION, updateTaskCommand.description)
        update.addValueIfSpecified(TASK.ASSIGNEE_ID, updateTaskCommand.assigneeId)
        update.addValueIfSpecified(TASK.REPORTER_ID, updateTaskCommand.reporterId)
        update.addValueIfSpecified(TASK.STATUS, updateTaskCommand.status)
        update.addValueIfSpecified(TASK.PRIORITY, updateTaskCommand.priority)

        update.addValue(TASK.UPDATED_AT, LocalDateTime.now())

        update.addConditions(TASK.ID.eq(id))
        update.execute()
    }
}

fun <R : Record, T> UpdateQuery<R>.addValueIfSpecified(
    tableField: TableField<R, T>,
    field: FieldUpdate<T>,
): UpdateQuery<R> = apply {
    when (field) {
        is FieldUpdate.Value<T> -> this.addValue(tableField, field.value)
        FieldUpdate.Undefined -> Unit
    }
}
