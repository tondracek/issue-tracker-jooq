package cz.developerthomas.issuetrackerjooq.task.api

import cz.developerthomas.issuetrackerjooq.task.domain.parseTaskId
import cz.developerthomas.issuetrackerjooq.task.dto.CreateTaskRequest
import cz.developerthomas.issuetrackerjooq.task.usecase.CreateTaskUC
import cz.developerthomas.issuetrackerjooq.task.usecase.GetTaskDetailUC
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

@Configuration
class TaskRouter(
    private val getTaskDetailUC: GetTaskDetailUC,
    private val createTaskUC: CreateTaskUC,
) {

    @Bean
    fun taskRoutes() = router {
        "/tasks".nest {
            GET("/{id}") { request ->
                val id = request.pathVariable("id").parseTaskId()

                val taskDetail = getTaskDetailUC(id)

                ok().body(taskDetail)
            }

            POST("") { request ->
                val createTaskRequest = request.body<CreateTaskRequest>()
                val createdTask = createTaskUC(createTaskRequest)

                ok().body(createdTask)
            }
        }
    }
}
