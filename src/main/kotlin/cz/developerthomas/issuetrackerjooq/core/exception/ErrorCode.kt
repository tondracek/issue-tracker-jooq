package cz.developerthomas.issuetrackerjooq.core.exception

enum class ErrorCode {
    // COMMON
    INVALID_ID,

    // TASK
    TASK_NOT_FOUND,
    INVALID_STATUS_TRANSITION,
    FORBIDDEN_STATUS_TRANSITION,

    // USER
    USER_NOT_FOUND,

    // Auth
    NOT_LOGGED_IN,
}