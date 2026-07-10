package cz.developerthomas.issuetrackerjooq.task.api

import cz.developerthomas.issuetrackerjooq.task.route.GetTaskDetailRoute
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.router

@Configuration
class TaskRoutes(
    private val getTaskDetailRoute: GetTaskDetailRoute,
) {

    @Bean
    fun taskRouter() = router {
        "/tasks".nest {
            getTaskDetailRoute
        }
    }
}