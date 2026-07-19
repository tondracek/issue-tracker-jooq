package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.core.IntegrationTest
import cz.developerthomas.issuetrackerjooq.core.helper.insertTask
import cz.developerthomas.issuetrackerjooq.core.helper.insertUser
import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.TaskBrowseFilter
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class BrowseTasksQueryTest : IntegrationTest() {

    @Autowired
    lateinit var dsl: DSLContext

    @Autowired
    lateinit var browseTasksQuery: BrowseTasksQuery

    @Test
    fun `returns all tasks when no filters are specified`() {
        val reporter = dsl.insertUser(name = "Reporter")

        val taskA = dsl.insertTask(title = "A Task", reporterId = reporter.id)
        val taskB = dsl.insertTask(title = "B Task", reporterId = reporter.id)
        val taskC = dsl.insertTask(title = "C Task", reporterId = reporter.id)

        val result = browse()

        assertThat(result.map { it.id }).containsExactly(taskA.id, taskB.id, taskC.id)
    }

    @Test
    fun `filters by status`() {
        val reporter = dsl.insertUser(name = "Reporter")

        dsl.insertTask(title = "TODO task", reporterId = reporter.id, status = TaskStatus.TODO)
        val inProgressTask = dsl.insertTask(
            title = "In progress task",
            reporterId = reporter.id,
            status = TaskStatus.IN_PROGRESS,
        )
        val doneTask = dsl.insertTask(title = "Done task", reporterId = reporter.id, status = TaskStatus.DONE)

        val result = browse(emptyFilter().copy(status = listOf(TaskStatus.IN_PROGRESS, TaskStatus.DONE)))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(inProgressTask.id, doneTask.id)
    }

    @Test
    fun `filters by multiple statuses`() {
        val reporter = dsl.insertUser(name = "Reporter")

        dsl.insertTask(title = "TODO task", reporterId = reporter.id, status = TaskStatus.TODO)
        val inProgressTask = dsl.insertTask(
            title = "In progress task",
            reporterId = reporter.id,
            status = TaskStatus.IN_PROGRESS,
        )
        val doneTask = dsl.insertTask(title = "Done task", reporterId = reporter.id, status = TaskStatus.DONE)

        val result = browse(emptyFilter().copy(status = listOf(TaskStatus.IN_PROGRESS, TaskStatus.DONE)))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(inProgressTask.id, doneTask.id)
    }

    @Test
    fun `filters by priority`() {
        val reporter = dsl.insertUser(name = "Reporter")

        dsl.insertTask(title = "Low priority task", reporterId = reporter.id, priority = TaskPriority.LOW)
        val highPriorityTask = dsl.insertTask(
            title = "High priority task",
            reporterId = reporter.id,
            priority = TaskPriority.HIGH,
        )
        val mediumPriorityTask = dsl.insertTask(
            title = "Medium priority task",
            reporterId = reporter.id,
            priority = TaskPriority.MEDIUM,
        )

        val result = browse(emptyFilter().copy(priority = listOf(TaskPriority.HIGH, TaskPriority.MEDIUM)))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(highPriorityTask.id, mediumPriorityTask.id)
    }

    @Test
    fun `filters by multiple priorities`() {
        val reporter = dsl.insertUser(name = "Reporter")

        dsl.insertTask(title = "Low priority task", reporterId = reporter.id, priority = TaskPriority.LOW)
        val highPriorityTask = dsl.insertTask(
            title = "High priority task",
            reporterId = reporter.id,
            priority = TaskPriority.HIGH,
        )
        val mediumPriorityTask = dsl.insertTask(
            title = "Medium priority task",
            reporterId = reporter.id,
            priority = TaskPriority.MEDIUM,
        )

        val result = browse(emptyFilter().copy(priority = listOf(TaskPriority.HIGH, TaskPriority.MEDIUM)))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(highPriorityTask.id, mediumPriorityTask.id)
    }

    @Test
    fun `filters by assignee`() {
        val reporter = dsl.insertUser(name = "Reporter")
        val assignee1 = dsl.insertUser(name = "Assignee 1")
        val assignee2 = dsl.insertUser(name = "Assignee 2")

        val task1 = dsl.insertTask(
            title = "Task for assignee 1",
            reporterId = reporter.id,
            assigneeId = assignee1.id,
        )
        val task2 = dsl.insertTask(
            title = "Task for assignee 2",
            reporterId = reporter.id,
            assigneeId = assignee2.id,
        )
        dsl.insertTask(title = "Unassigned task", reporterId = reporter.id, assigneeId = null)

        val result = browse(emptyFilter().copy(assignee = listOf(assignee1.id, assignee2.id)))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(task1.id, task2.id)
    }

    @Test
    fun `filters by multiple assignees`() {
        val reporter = dsl.insertUser(name = "Reporter")
        val assignee1 = dsl.insertUser(name = "Assignee 1")
        val assignee2 = dsl.insertUser(name = "Assignee 2")

        val task1 = dsl.insertTask(
            title = "Task for assignee 1",
            reporterId = reporter.id,
            assigneeId = assignee1.id,
        )
        val task2 = dsl.insertTask(
            title = "Task for assignee 2",
            reporterId = reporter.id,
            assigneeId = assignee2.id,
        )
        dsl.insertTask(title = "Unassigned task", reporterId = reporter.id, assigneeId = null)

        val result = browse(emptyFilter().copy(assignee = listOf(assignee1.id, assignee2.id)))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(task1.id, task2.id)
    }

    @Test
    fun `filters unassigned tasks when assignee filter contains null`() {
        val reporter = dsl.insertUser(name = "Reporter")
        val assignee = dsl.insertUser(name = "Assignee")

        val assignedTask = dsl.insertTask(
            title = "Assigned task",
            reporterId = reporter.id,
            assigneeId = assignee.id,
        )
        val unassignedTask1 = dsl.insertTask(
            title = "Unassigned task 1",
            reporterId = reporter.id,
            assigneeId = null,
        )
        val unassignedTask2 = dsl.insertTask(
            title = "Unassigned task 2",
            reporterId = reporter.id,
            assigneeId = null,
        )

        val result = browse(emptyFilter().copy(assignee = listOf(null)))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(unassignedTask1.id, unassignedTask2.id)
        assertThat(result.map { it.id }).doesNotContain(assignedTask.id)
    }

    @Test
    fun `filters text in title and description case insensitively`() {
        val reporter = dsl.insertUser(name = "Reporter")

        val titleMatch = dsl.insertTask(
            title = "Implement Feature Flag",
            reporterId = reporter.id,
        )
        val descriptionMatch = dsl.insertTask(
            title = "Release prep",
            description = "This task mentions FEATure in the description",
            reporterId = reporter.id,
        )
        dsl.insertTask(
            title = "Unrelated task",
            description = "No keyword here",
            reporterId = reporter.id,
        )

        val result = browse(emptyFilter().copy(text = "feature"))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(titleMatch.id, descriptionMatch.id)
    }

    @Test
    fun `combines multiple filters correctly`() {
        val reporter = dsl.insertUser(name = "Reporter")
        val assignee = dsl.insertUser(name = "Assignee")

        dsl.insertTask(
            title = "Database setup",
            reporterId = reporter.id,
            assigneeId = assignee.id,
            status = TaskStatus.TODO,
            priority = TaskPriority.HIGH,
        )
        val matchingTask = dsl.insertTask(
            title = "Implement database schema",
            reporterId = reporter.id,
            assigneeId = assignee.id,
            status = TaskStatus.IN_PROGRESS,
            priority = TaskPriority.HIGH,
        )
        dsl.insertTask(
            title = "Implement database queries",
            reporterId = reporter.id,
            assigneeId = null,
            status = TaskStatus.IN_PROGRESS,
            priority = TaskPriority.HIGH,
        )
        dsl.insertTask(
            title = "Implement database backup",
            reporterId = reporter.id,
            assigneeId = assignee.id,
            status = TaskStatus.IN_PROGRESS,
            priority = TaskPriority.MEDIUM,
        )

        val result = browse(
            emptyFilter().copy(
                text = "schema",
                status = listOf(TaskStatus.IN_PROGRESS),
                priority = listOf(TaskPriority.HIGH),
                assignee = listOf(assignee.id),
            )
        )

        assertThat(result).hasSize(1)
        assertThat(result.single().id).isEqualTo(matchingTask.id)
    }

    @Test
    fun `returns an empty list when nothing matches`() {
        val reporter = dsl.insertUser(name = "Reporter")

        dsl.insertTask(title = "Task 1", reporterId = reporter.id, status = TaskStatus.TODO)

        val result = browse(
            emptyFilter().copy(
                text = "missing",
                status = listOf(TaskStatus.DONE),
            )
        )

        assertThat(result).isEmpty()
    }

    @Test
    fun `sorts by title ascending by default`() {
        val reporter = dsl.insertUser(name = "Reporter")

        val taskC = dsl.insertTask(title = "C Task", reporterId = reporter.id)
        val taskA = dsl.insertTask(title = "A Task", reporterId = reporter.id)
        val taskB = dsl.insertTask(title = "B Task", reporterId = reporter.id)

        val result = browse()

        assertThat(result.map { it.id }).containsExactly(taskA.id, taskB.id, taskC.id)
    }

    @Test
    fun `paginates with limit and offset`() {
        val reporter = dsl.insertUser(name = "Reporter")

        val taskA = dsl.insertTask(title = "A Task", reporterId = reporter.id)
        val taskB = dsl.insertTask(title = "B Task", reporterId = reporter.id)
        val taskC = dsl.insertTask(title = "C Task", reporterId = reporter.id)
        val taskD = dsl.insertTask(title = "D Task", reporterId = reporter.id)

        val firstPage = browse(emptyFilter().copy(limit = 2, offset = 0))
        val secondPage = browse(emptyFilter().copy(limit = 2, offset = 2))

        assertThat(firstPage.map { it.id }).containsExactly(taskA.id, taskB.id)
        assertThat(secondPage.map { it.id }).containsExactly(taskC.id, taskD.id)
    }

    @Test
    fun `ignores null and empty optional filters`() {
        val reporter = dsl.insertUser(name = "Reporter")

        val taskA = dsl.insertTask(title = "A Task", reporterId = reporter.id)
        val taskB = dsl.insertTask(title = "B Task", reporterId = reporter.id)

        val resultWithNulls = browse(
            emptyFilter().copy(
                text = null,
                assignee = null,
                status = null,
                priority = null,
            )
        )
        val resultWithEmptyCollections = browse(
            emptyFilter().copy(
                text = "",
                assignee = emptyList(),
                status = emptyList(),
                priority = emptyList(),
            )
        )

        assertThat(resultWithNulls.map { it.id }).containsExactly(taskA.id, taskB.id)
        assertThat(resultWithEmptyCollections.map { it.id }).containsExactly(taskA.id, taskB.id)
    }

    private fun browse(filter: TaskBrowseFilter = emptyFilter()) = browseTasksQuery(filter)

    private fun emptyFilter() = TaskBrowseFilter(
        text = null,
        assignee = null,
        status = null,
        priority = null,
        offset = null,
        limit = null,
        orderBy = null,
        orderDirection = null,
    )
}
