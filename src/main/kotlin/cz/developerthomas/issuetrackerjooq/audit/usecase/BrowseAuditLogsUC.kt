package cz.developerthomas.issuetrackerjooq.audit.usecase

import cz.developerthomas.issuetrackerjooq.audit.domain.AuditBrowseFilter
import cz.developerthomas.issuetrackerjooq.audit.query.BrowseAuditLogsQuery
import org.springframework.stereotype.Service

@Service
class BrowseAuditLogsUC(
    private val browseAuditLogsQuery: BrowseAuditLogsQuery,
) {
    operator fun invoke(filter: AuditBrowseFilter) =
        browseAuditLogsQuery(filter)
}

