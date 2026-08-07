package cz.developerthomas.issuetrackerjooq.task.api

import cz.developerthomas.issuetrackerjooq.core.validation.validBody
import cz.developerthomas.issuetrackerjooq.task.domain.TaskBrowseFilter
import cz.developerthomas.issuetrackerjooq.task.domain.parseTaskId
import cz.developerthomas.issuetrackerjooq.task.dto.CreateTaskRequest
import cz.developerthomas.issuetrackerjooq.task.dto.UpdateTaskRequest
import cz.developerthomas.issuetrackerjooq.task.usecase.BrowseTasksUC
import cz.developerthomas.issuetrackerjooq.task.usecase.CreateTaskUC
import cz.developerthomas.issuetrackerjooq.task.usecase.GetTaskDetailUC
import cz.developerthomas.issuetrackerjooq.task.usecase.UpdateTaskUC
import jakarta.validation.Validator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.router

@Configuration
class TaskRouter(
    private val getTaskDetailUC: GetTaskDetailUC,
    private val createTaskUC: CreateTaskUC,
    private val browseTasksUC: BrowseTasksUC,
    private val updateTaskUC: UpdateTaskUC,
    private val validator: Validator,
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
                val createTaskRequest = request.validBody<CreateTaskRequest>(validator)
                val createdTask = createTaskUC(createTaskRequest)

                ok().body(createdTask)
            }

            PATCH("/{id}") { request ->
                val id = request.pathVariable("id").parseTaskId()
                val updateTaskRequest = request.validBody<UpdateTaskRequest>(validator)
                val updatedTask = updateTaskUC(id, updateTaskRequest)

                ok().body(updatedTask)
            }

            POST("/browse") { request ->
                val filter = request.validBody<TaskBrowseFilter>(validator)

                val tasks = browseTasksUC(filter)

                ok().body(tasks)
            }
        }
    }
}
