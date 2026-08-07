package cz.developerthomas.issuetrackerjooq.auth.usecase

import cz.developerthomas.issuetrackerjooq.auth.dto.RegisterRequest
import cz.developerthomas.issuetrackerjooq.auth.dto.toCommand
import cz.developerthomas.issuetrackerjooq.auth.query.CreateUserCommand
import cz.developerthomas.issuetrackerjooq.core.identityprovider.IdentityProvider
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.springframework.stereotype.Service

@Service
class RegisterUserUC(
    private val createUserCommand: CreateUserCommand,
    private val identityProvider: IdentityProvider,
) {

    operator fun invoke(request: RegisterRequest): UserId {
        val authId = identityProvider.createUser(
            request.email,
            request.password,
        )

        val createUserCommand = request.toCommand(
            id = UserId.newId(),
            authId = authId
        )

        try {
            return createUserCommand(createUserCommand)
        } catch (e: RuntimeException) {
            identityProvider.deleteUser(authId)
            throw e
        }
    }
}