package cz.developerthomas.issuetrackerjooq.taskcomment.query

import cz.developerthomas.issuetrackerjooq.tables.TaskComment.Companion.TASK_COMMENT
import cz.developerthomas.issuetrackerjooq.tables.records.TaskCommentRecord
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.CreateTaskComment
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.TaskComment
import org.jooq.DSLContext
import org.jooq.Records
import org.springframework.stereotype.Repository

@Repository
class CreateTaskCommentCommand(
    private val dsl: DSLContext,
) {

    operator fun invoke(createTaskComment: CreateTaskComment): TaskComment {
        val record = createTaskComment.toRecord()

        return dsl.insertInto(TASK_COMMENT)
            .set(record)
            .returningResult(
                TASK_COMMENT.ID,
                TASK_COMMENT.CONTENT,
                TASK_COMMENT.AUTHOR_ID,
                TASK_COMMENT.TASK_ID,
                TASK_COMMENT.CREATED_AT,
            )
            .fetchSingle(Records.mapping(::TaskComment))
    }
}

private fun CreateTaskComment.toRecord() = TaskCommentRecord(
    id = id,
    content = content,
    authorId = authorId,
    taskId = taskId,
)






