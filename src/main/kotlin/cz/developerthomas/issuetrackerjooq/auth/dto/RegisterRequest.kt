package cz.developerthomas.issuetrackerjooq.auth.dto

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId
import cz.developerthomas.issuetrackerjooq.auth.domain.CreateUserCommand
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @Email
    val email: String,
    @NotBlank
    val name: String,
    val jobTitle: String? = null,
    @NotBlank
    @Size(min = 8, max = 128)
    val password: String,
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