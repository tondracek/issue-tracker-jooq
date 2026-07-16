package cz.developerthomas.issuetrackerjooq.sample

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.tables.records.TaskCommentRecord
import cz.developerthomas.issuetrackerjooq.tables.records.TaskRecord
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.tables.references.TASK_COMMENT
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.TaskCommentId
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class TaskGenerator(
    private val dsl: DSLContext,
) {

    companion object {
        private val DEMO_TASK = TaskRecord(
            id = TaskId.fromString("6a173437-23e2-466e-815f-3ed1a9298066"),
            title = "Demo Task",
            description = "This is a demo task for testing purposes.",
            status = TaskStatus.TODO,
            priority = TaskPriority.MEDIUM,
            assigneeId = UserGenerator.SOFTWARE_ENGINEER.id,
            reporterId = UserGenerator.PRODUCT_MANAGER.id,
        )

        private val DEMO_TASK_COMMENTS = listOf(
            TaskCommentRecord(
                id = TaskCommentId.fromString("46fb47ec-5329-4624-814b-0882ded4eb90"),
                taskId = DEMO_TASK.id,
                content = "This is a comment on the demo task.",
                authorId = UserGenerator.PRODUCT_MANAGER.id,
            ),
            TaskCommentRecord(
                id = TaskCommentId.fromString("acdbbc8c-8bbb-4ea2-9195-1b986c6a8e90"),
                taskId = DEMO_TASK.id,
                content = "This is another comment on the demo task.",
                authorId = UserGenerator.SOFTWARE_ENGINEER.id,
            )
        )

        private val DEMO_TASK_2 = TaskRecord(
            id = TaskId.fromString("3a07cd9e-a767-4b43-901c-3367cce4c5c5"),
            title = "Demo Task 2",
            description = "This is another demo task for testing purposes.",
            status = TaskStatus.IN_PROGRESS,
            priority = TaskPriority.HIGH,
            assigneeId = null,
            reporterId = UserGenerator.PRODUCT_MANAGER.id,
        )
    }

    fun generate() {
        dsl.upsertSample(DEMO_TASK, TASK, TASK.ID)
        dsl.upsertSample(DEMO_TASK_COMMENTS[0], TASK_COMMENT, TASK_COMMENT.ID)
        dsl.upsertSample(DEMO_TASK_COMMENTS[1], TASK_COMMENT, TASK_COMMENT.ID)

        dsl.upsertSample(DEMO_TASK_2, TASK, TASK.ID)
    }
}