package cz.developerthomas.issuetrackerjooq.audit.api

import cz.developerthomas.issuetrackerjooq.audit.domain.AuditBrowseFilter
import cz.developerthomas.issuetrackerjooq.audit.usecase.BrowseAuditLogsUC
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

@Configuration
class AuditRouter(
    private val browseAuditLogsUC: BrowseAuditLogsUC,
) {

    @Bean
    fun auditRoutes() = router {
        "/audit".nest {
            POST("/browse") { request ->
                val filter = request.body<AuditBrowseFilter>()

                val auditLogs = browseAuditLogsUC(filter)

                ok().body(auditLogs)
            }
        }
    }
}

