package cz.developerthomas.issuetrackerjooq.user.view

import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.SelectField
import org.jooq.impl.DSL.row
import java.util.*

data class UserPreview(
    val id: UserId,
    val name: String,
    val jobTitle: String? = null,
)

fun userPreviewRowNullable(assignee: cz.developerthomas.issuetrackerjooq.tables.UserPreview): SelectField<UserPreview?> =
    row(
        assignee.ID,
        assignee.NAME,
        assignee.JOB_TITLE,
    ).mapping { id: UUID?, name: String?, jobTitle: String? ->
        when {
            id == null || name == null -> null
            else -> UserPreview(
                id = UserId(id),
                name = name,
                jobTitle = jobTitle,
            )
        }
    }

fun userPreviewRow(assignee: cz.developerthomas.issuetrackerjooq.tables.UserPreview): SelectField<UserPreview> =
    row(
        assignee.ID,
        assignee.NAME,
        assignee.JOB_TITLE,
    ).mapping { id: UUID?, name: String?, jobTitle: String? ->
        UserPreview(
            id = UserId(id!!),
            name = name!!,
            jobTitle = jobTitle,
        )
    }
