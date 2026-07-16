package cz.developerthomas.issuetrackerjooq.user.view

import cz.developerthomas.issuetrackerjooq.tables.AppUser
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.SelectField
import org.jooq.impl.DSL.row

data class UserPreview(
    val id: UserId,
    val name: String,
    val jobTitle: String? = null,
)

fun userPreviewRowNullable(assignee: AppUser): SelectField<UserPreview?> =
    row(
        assignee.ID,
        assignee.NAME,
        assignee.JOB_TITLE,
    ).mapping { id: UserId?, name: String?, jobTitle: String? ->
        when {
            id == null || name == null -> null
            else -> UserPreview(id = id, name = name, jobTitle = jobTitle)
        }
    }

fun userPreviewRow(assignee: AppUser): SelectField<UserPreview> =
    row(
        assignee.ID,
        assignee.NAME,
        assignee.JOB_TITLE,
    ).mapping { id: UserId?, name: String?, jobTitle: String? ->
        UserPreview(
            id = requireNotNull(id),
            name = requireNotNull(name),
            jobTitle = jobTitle,
        )
    }
