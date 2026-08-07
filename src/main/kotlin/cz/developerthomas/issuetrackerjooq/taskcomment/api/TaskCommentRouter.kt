package cz.developerthomas.issuetrackerjooq.taskcomment.api

import cz.developerthomas.issuetrackerjooq.core.validation.validBody
import cz.developerthomas.issuetrackerjooq.task.domain.parseTaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.dto.CreateTaskCommentRequest
import cz.developerthomas.issuetrackerjooq.taskcomment.usecase.CreateTaskCommentUC
import jakarta.validation.Validator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.router

@Configuration
class TaskCommentRouter(
    private val createTaskCommentUC: CreateTaskCommentUC,
    private val validator: Validator,
) {

    @Bean
    fun taskCommentRoutes() = router {
        "/tasks".nest {
            POST("/{id}/comments") { request ->
                val id = request.pathVariable("id").parseTaskId()
                val createReq = request.validBody<CreateTaskCommentRequest>(validator)

                val created = createTaskCommentUC(id, createReq)

                ok().body(created)
            }
        }
    }
}

