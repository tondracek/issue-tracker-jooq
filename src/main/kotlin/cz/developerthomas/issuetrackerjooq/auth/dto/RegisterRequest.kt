package cz.developerthomas.issuetrackerjooq.auth.dto

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId
import cz.developerthomas.issuetrackerjooq.auth.domain.CreateUserCommand
import cz.developerthomas.issuetrackerjooq.user.domain.UserId

data class RegisterRequest(
    val email: String,
    val name: String,
    val jobTitle: String? = null,
    val password: String,
    val passwordConfirmation: String,
)

fun RegisterRequest.toCommand(
    id: UserId,
    authId: AuthId,
) = CreateUserCommand(
    id = id,
    email = email,
    name = name,
    jobTitle = jobTitle,
    authId = authId,
)