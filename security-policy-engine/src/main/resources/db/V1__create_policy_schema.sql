CREATE TABLE policies (
    id              UUID PRIMARY KEY,
    tenant_id       UUID NOT NULL,
    name            VARCHAR(120) NOT NULL,
    description     TEXT,
    current_version INT  NOT NULL DEFAULT 1,
    enabled         BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_policies_tenant_name UNIQUE (tenant_id, name)
);
CREATE INDEX idx_policies_tenant ON policies(tenant_id);

CREATE TABLE policy_versions (
    id         UUID PRIMARY KEY,
    policy_id  UUID NOT NULL REFERENCES policies(id) ON DELETE CASCADE,
    version    INT  NOT NULL,
    rego       TEXT NOT NULL,
    author     VARCHAR(120),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_policy_versions UNIQUE (policy_id, version)
);
