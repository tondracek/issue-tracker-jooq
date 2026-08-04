CREATE TYPE AUDIT_EVENT AS ENUM (
    'TASK_CREATED',
    'TASK_UPDATED',

    'COMMENT_CREATED',

    'USER_CREATED'
    );

CREATE TABLE audit_log
(
    id           UUID PRIMARY KEY,
    entity_id    UUID        NOT NULL,
    display_name TEXT,
    author_id    UUID        NOT NULL REFERENCES app_user (id),
    action       AUDIT_EVENT NOT NULL,
    payload      JSONB       NOT NULL,
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);
