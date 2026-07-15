package cz.developerthomas.issuetrackerjooq.task.api

import cz.developerthomas.issuetrackerjooq.task.domain.parseTaskId
import cz.developerthomas.issuetrackerjooq.task.usecase.GetTaskDetailUC
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.router

@Configuration
class TaskRoutes(
    private val getTaskDetailUC: GetTaskDetailUC,
) {

    @Bean
    fun taskRouter() = router {
        "/tasks".nest {
            GET("/{id}") { request ->
                val id = request.pathVariable("id").parseTaskId()

                val taskDetail = getTaskDetailUC(id)

                ok().body(taskDetail)
            }
        }
    }
}