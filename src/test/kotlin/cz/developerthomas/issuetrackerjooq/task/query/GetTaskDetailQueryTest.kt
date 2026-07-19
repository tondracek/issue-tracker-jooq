package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.core.IntegrationTest
import cz.developerthomas.issuetrackerjooq.core.helper.insertTask
import cz.developerthomas.issuetrackerjooq.core.helper.insertUser
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class GetTaskDetailQueryTest : IntegrationTest() {

    @Autowired
    lateinit var dsl: DSLContext

    @Autowired
    lateinit var getTaskDetailQuery: GetTaskDetailQuery

    @Test
    fun `returns task detail`() {
        val reporter = dsl.insertUser(name = "Reporter")

        val assignee = dsl.insertUser(name = "Assignee")

        val task = dsl.insertTask(
            title = "Implement tests",
            description = "Write integration tests",
            reporterId = reporter.id,
            assigneeId = assignee.id,
        )

        val result = getTaskDetailQuery(task.id)

        assertThat(result).isNotNull
        assertThat(result.id).isEqualTo(task.id)
        assertThat(result.title).isEqualTo(task.title)
        assertThat(result.description).isEqualTo(task.description)

        assertThat(result.reporter.name).isEqualTo(reporter.name)
        if (task.assigneeId != null)
            assertThat(result.assignee!!.name).isEqualTo(assignee.name)
    }
}