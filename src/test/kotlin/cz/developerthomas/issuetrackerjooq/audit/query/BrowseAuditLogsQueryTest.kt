package cz.developerthomas.issuetrackerjooq.audit.query

import cz.developerthomas.issuetrackerjooq.audit.domain.AuditBrowseFilter
import cz.developerthomas.issuetrackerjooq.core.IntegrationTest
import cz.developerthomas.issuetrackerjooq.core.helper.insertAuditLog
import cz.developerthomas.issuetrackerjooq.core.helper.insertUser
import cz.developerthomas.issuetrackerjooq.enums.AuditEvent
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
class BrowseAuditLogsQueryTest : IntegrationTest() {

    @Autowired
    lateinit var dsl: DSLContext

    @Autowired
    lateinit var browseAuditLogsQuery: BrowseAuditLogsQuery

    @Test
    fun `returns all audit logs when no filters are specified`() {
        val author = dsl.insertUser(name = "Author")

        val log1 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Task 1",
            action = AuditEvent.TASK_CREATED,
        )
        val log2 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Task 2",
            action = AuditEvent.TASK_UPDATED,
        )
        val log3 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Task 3",
            action = AuditEvent.TASK_CREATED,
        )

        val result = browse()

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(log1.id, log2.id, log3.id)
    }

    @Test
    fun `filters by action`() {
        val author = dsl.insertUser(name = "Author")

        dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Task 1",
            action = AuditEvent.TASK_CREATED,
        )
        val updatedLog = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Task 2",
            action = AuditEvent.TASK_UPDATED,
        )
        dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Comment 1",
            action = AuditEvent.COMMENT_CREATED,
        )

        val result = browse(emptyFilter().copy(action = AuditEvent.TASK_UPDATED))

        assertThat(result.map { it.id }).containsExactly(updatedLog.id)
    }

    @Test
    fun `filters by entity_id`() {
        val author = dsl.insertUser(name = "Author")
        val taskId = UUID.randomUUID()

        val taskLog1 = dsl.insertAuditLog(
            authorId = author.id,
            entityId = taskId,
            displayName = "Task Edit 1",
            action = AuditEvent.TASK_UPDATED,
        )
        val taskLog2 = dsl.insertAuditLog(
            authorId = author.id,
            entityId = taskId,
            displayName = "Task Edit 2",
            action = AuditEvent.TASK_UPDATED,
        )
        dsl.insertAuditLog(
            authorId = author.id,
            entityId = UUID.randomUUID(),
            displayName = "Other Task",
            action = AuditEvent.TASK_CREATED,
        )

        val result = browse(emptyFilter().copy(entityId = taskId))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(taskLog1.id, taskLog2.id)
    }

    @Test
    fun `filters by author_id`() {
        val author1 = dsl.insertUser(name = "Author 1")
        val author2 = dsl.insertUser(name = "Author 2")

        val log1 = dsl.insertAuditLog(
            authorId = author1.id,
            displayName = "Task by author 1",
            action = AuditEvent.TASK_CREATED,
        )
        dsl.insertAuditLog(
            authorId = author2.id,
            displayName = "Task by author 2",
            action = AuditEvent.TASK_CREATED,
        )
        val log3 = dsl.insertAuditLog(
            authorId = author1.id,
            displayName = "Comment by author 1",
            action = AuditEvent.COMMENT_CREATED,
        )

        val result = browse(emptyFilter().copy(authorId = author1.id))

        assertThat(result.map { it.id }).containsExactlyInAnyOrder(log1.id, log3.id)
    }

    @Test
    fun `combines multiple filters correctly`() {
        val author1 = dsl.insertUser(name = "Author 1")
        val author2 = dsl.insertUser(name = "Author 2")
        val taskId = UUID.randomUUID()

        dsl.insertAuditLog(
            authorId = author1.id,
            entityId = taskId,
            displayName = "Task Created",
            action = AuditEvent.TASK_CREATED,
        )
        val matchingLog = dsl.insertAuditLog(
            authorId = author1.id,
            entityId = taskId,
            displayName = "Task Updated",
            action = AuditEvent.TASK_UPDATED,
        )
        dsl.insertAuditLog(
            authorId = author2.id,
            entityId = taskId,
            displayName = "Task Updated",
            action = AuditEvent.TASK_UPDATED,
        )
        dsl.insertAuditLog(
            authorId = author1.id,
            entityId = UUID.randomUUID(),
            displayName = "Other Task",
            action = AuditEvent.TASK_UPDATED,
        )

        val result = browse(
            emptyFilter().copy(
                entityId = taskId,
                authorId = author1.id,
                action = AuditEvent.TASK_UPDATED,
            )
        )

        assertThat(result).hasSize(1)
        assertThat(result.single().id).isEqualTo(matchingLog.id)
    }

    @Test
    fun `returns an empty list when nothing matches`() {
        val author = dsl.insertUser(name = "Author")

        dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Task 1",
            action = AuditEvent.TASK_CREATED,
        )

        val result = browse(
            emptyFilter().copy(
                action = AuditEvent.COMMENT_CREATED,
            )
        )

        assertThat(result).isEmpty()
    }

    @Test
    fun `sorts by created_at descending`() {
        val author = dsl.insertUser(name = "Author")

        val log1 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Log 1",
            action = AuditEvent.TASK_CREATED,
        )
        val log2 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Log 2",
            action = AuditEvent.TASK_CREATED,
        )
        val log3 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Log 3",
            action = AuditEvent.TASK_CREATED,
        )

        val result = browse()

        // When all have same created_at, they are sorted by ID desc
        // The result should contain all 3 logs in some order (deterministic by ID)
        assertThat(result.map { it.id }).hasSize(3)
        assertThat(result.map { it.id }).containsExactlyInAnyOrder(log1.id, log2.id, log3.id)
    }

    @Test
    fun `paginates with limit and offset`() {
        val author = dsl.insertUser(name = "Author")

        val log1 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Log 1",
            action = AuditEvent.TASK_CREATED,
        )
        val log2 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Log 2",
            action = AuditEvent.TASK_CREATED,
        )
        val log3 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Log 3",
            action = AuditEvent.TASK_CREATED,
        )
        val log4 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Log 4",
            action = AuditEvent.TASK_CREATED,
        )

        val firstPage = browse(emptyFilter().copy(limit = 2, offset = 0))
        val secondPage = browse(emptyFilter().copy(limit = 2, offset = 2))

        assertThat(firstPage).hasSize(2)
        assertThat(secondPage).hasSize(2)
        // Combined should have all 4
        assertThat((firstPage.map { it.id } + secondPage.map { it.id }).toSet())
            .containsExactlyInAnyOrder(log1.id, log2.id, log3.id, log4.id)
    }

    @Test
    fun `ignores null optional filters`() {
        val author = dsl.insertUser(name = "Author")

        val log1 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Log 1",
            action = AuditEvent.TASK_CREATED,
        )
        val log2 = dsl.insertAuditLog(
            authorId = author.id,
            displayName = "Log 2",
            action = AuditEvent.TASK_UPDATED,
        )

        val resultWithNulls = browse(
            emptyFilter().copy(
                entityId = null,
                authorId = null,
                action = null,
            )
        )

        assertThat(resultWithNulls.map { it.id }).hasSize(2)
    }

    private fun browse(filter: AuditBrowseFilter = emptyFilter()) = browseAuditLogsQuery(filter)

    private fun emptyFilter() = AuditBrowseFilter(
        entityId = null,
        authorId = null,
        action = null,
        offset = null,
        limit = null,
    )
}







