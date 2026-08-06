package cz.developerthomas.issuetrackerjooq.audit.query

import cz.developerthomas.issuetrackerjooq.audit.domain.AuditBrowseFilter
import cz.developerthomas.issuetrackerjooq.audit.view.AuditLogListItemView
import cz.developerthomas.issuetrackerjooq.tables.references.APP_USER
import cz.developerthomas.issuetrackerjooq.tables.references.AUDIT_LOG
import cz.developerthomas.issuetrackerjooq.user.view.userPreviewRow
import org.jooq.*
import org.jooq.impl.DSL.trueCondition
import org.springframework.stereotype.Repository

@Repository
class BrowseAuditLogsQuery(
    private val dsl: DSLContext,
) {

    operator fun invoke(filter: AuditBrowseFilter): List<AuditLogListItemView> =
        dsl.select(
            AUDIT_LOG.ID,
            AUDIT_LOG.ENTITY_ID,
            AUDIT_LOG.DISPLAY_NAME,
            userPreviewRow(APP_USER),
            AUDIT_LOG.ACTION,
            AUDIT_LOG.PAYLOAD,
            AUDIT_LOG.CREATED_AT,
        )
            .from(AUDIT_LOG)
            .join(APP_USER).on(AUDIT_LOG.AUTHOR_ID.eq(APP_USER.ID))
            .where(
                AUDIT_LOG.ENTITY_ID.eqOptional(filter.entityId),
                AUDIT_LOG.AUTHOR_ID.eqOptional(filter.authorId),
                AUDIT_LOG.ACTION.eqOptional(filter.action),
            )
            .orderBy(
                AUDIT_LOG.CREATED_AT.desc(),
                AUDIT_LOG.ID.desc()  // Secondary sort for deterministic ordering
            )
            .limitOffsetOptional(filter.limit, filter.offset)
            .fetch(Records.mapping(::AuditLogListItemView))
}

private fun <T> Field<T?>.eqOptional(value: T?): Condition {
    if (value == null) return trueCondition()
    return this.eq(value)
}

fun <R : Record> SelectLimitStep<R>.limitOffsetOptional(
    limit: Int?,
    offset: Int?,
) = when {
    limit == null && offset == null -> this
    limit == null && offset != null -> offset(offset)
    limit != null && offset == null -> limit(limit)
    else -> limit(offset, limit)
}

