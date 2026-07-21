ALTER TABLE app_user
    ADD COLUMN auth_id UUID UNIQUE;

CREATE INDEX idx_app_user_auth_id ON app_user (auth_id);