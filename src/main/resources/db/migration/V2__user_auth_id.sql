ALTER TABLE app_user
    ADD COLUMN auth_id VARCHAR(255) NOT NULL UNIQUE;

CREATE INDEX idx_app_user_auth_id ON app_user (auth_id);

CREATE TYPE app_role AS ENUM (
    'USER',
    'ADMIN'
);

ALTER TABLE app_user
    ADD COLUMN role app_role NOT NULL DEFAULT 'USER';