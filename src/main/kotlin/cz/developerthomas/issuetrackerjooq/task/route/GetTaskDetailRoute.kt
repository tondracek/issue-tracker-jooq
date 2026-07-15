package cz.developerthomas.issuetrackerjooq.task.route

import cz.developerthomas.issuetrackerjooq.task.domain.parseTaskId
import cz.developerthomas.issuetrackerjooq.task.usecase.GetTaskDetailUC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.RouterFunctionDsl

@Component
class GetTaskDetailRoute(
    private val getTaskDetailUC: GetTaskDetailUC,
) {

    operator fun RouterFunctionDsl.invoke(): Unit = GET("/{id}") { request ->
        val id = request.pathVariable("id").parseTaskId()

        val taskDetail = getTaskDetailUC(id)

        ok().body(taskDetail)
    }
}


