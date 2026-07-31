package cz.developerthomas.issuetrackerjooq.task.api

import cz.developerthomas.issuetrackerjooq.task.domain.TaskBrowseFilter
import cz.developerthomas.issuetrackerjooq.task.domain.parseTaskId
import cz.developerthomas.issuetrackerjooq.task.dto.CreateTaskRequest
import cz.developerthomas.issuetrackerjooq.task.dto.UpdateTaskRequest
import cz.developerthomas.issuetrackerjooq.task.usecase.BrowseTasksUC
import cz.developerthomas.issuetrackerjooq.task.usecase.CreateTaskUC
import cz.developerthomas.issuetrackerjooq.task.usecase.GetTaskDetailUC
import cz.developerthomas.issuetrackerjooq.task.usecase.UpdateTaskUC
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

@Configuration
class TaskRouter(
    private val getTaskDetailUC: GetTaskDetailUC,
    private val createTaskUC: CreateTaskUC,
    private val browseTasksUC: BrowseTasksUC,
    private val updateTaskUC: UpdateTaskUC,
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

            PATCH("/{id}") { request ->
                val id = request.pathVariable("id").parseTaskId()
                val updateTaskRequest = request.body<UpdateTaskRequest>()
                val updatedTask = updateTaskUC(id, updateTaskRequest)

                ok().body(updatedTask)
            }

            POST("/browse") { request ->
                val filter = request.body<TaskBrowseFilter>()

                val tasks = browseTasksUC(filter)

                ok().body(tasks)
            }
        }
    }
}
