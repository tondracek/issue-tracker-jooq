-------------
--  ENUMS  --
-------------

CREATE TYPE task_status AS ENUM (
    'TODO',
    'IN_PROGRESS',
    'DONE',
    'CANCELLED'
    );

CREATE TYPE task_priority AS ENUM (
    'LOW',
    'MEDIUM',
    'HIGH',
    'CRITICAL'
    );

--------------
-- ENTITIES --
--------------

CREATE TABLE app_user
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL,
    job_title  VARCHAR(100),
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE task
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(255)  NOT NULL,
    description TEXT,
    assignee_id UUID,
    reporter_id UUID          NOT NULL,
    status      task_status   NOT NULL DEFAULT 'TODO',
    priority    task_priority NOT NULL,
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_task_assignee FOREIGN KEY (assignee_id) REFERENCES app_user (id)
        ON DELETE SET NULL,
    CONSTRAINT fk_task_reporter FOREIGN KEY (reporter_id) REFERENCES app_user (id)
        ON DELETE RESTRICT
);

CREATE TABLE task_comment
(
    id         UUID PRIMARY KEY,
    content    TEXT      NOT NULL,
    author_id  UUID      NOT NULL,
    task_id    UUID      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_task_comment_author FOREIGN KEY (author_id) REFERENCES app_user (id),
    CONSTRAINT fk_task_comment_task FOREIGN KEY (task_id) REFERENCES task (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_task_assignee ON task (assignee_id);
CREATE INDEX idx_task_reporter ON task (reporter_id);
CREATE INDEX idx_task_status ON task (status);

CREATE INDEX idx_task_comment_task ON task_comment (task_id);
