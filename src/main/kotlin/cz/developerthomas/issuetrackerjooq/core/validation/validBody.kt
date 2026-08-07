package cz.developerthomas.issuetrackerjooq.core.validation

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.body

inline fun <reified T : Any> ServerRequest.validBody(
    validator: Validator,
): T = body<T>()
    .also {
        val violations = validator.validate(it)
        if (violations.isNotEmpty())
            throw ConstraintViolationException(violations)
    }
