
CREATE TABLE IF NOT EXISTS policy (
    id           UUID PRIMARY KEY,
    name         VARCHAR(255) NOT NULL UNIQUE,
    description  TEXT,
    rego_source  TEXT NOT NULL,
    version      INT  NOT NULL DEFAULT 1,
    enabled      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS policy_binding (
    id           UUID PRIMARY KEY,
    policy_id    UUID NOT NULL REFERENCES policy(id) ON DELETE CASCADE,
    subject_type VARCHAR(64)  NOT NULL,
    subject_id   VARCHAR(256) NOT NULL,
    resource     VARCHAR(256) NOT NULL,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_policy_binding_subject  ON policy_binding (subject_type, subject_id);
CREATE INDEX IF NOT EXISTS idx_policy_binding_resource ON policy_binding (resource);
