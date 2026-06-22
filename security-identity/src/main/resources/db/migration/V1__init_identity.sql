CREATE TABLE IF NOT EXISTS identities (
    id              UUID PRIMARY KEY,
    tenant_id       VARCHAR(64)  NOT NULL,
    username        VARCHAR(128) NOT NULL,
    email           VARCHAR(256) NOT NULL,
    status          VARCHAR(32)  NOT NULL,
    failed_attempts INT          NOT NULL DEFAULT 0,
    created_at      TIMESTAMPTZ  NOT NULL,
    updated_at      TIMESTAMPTZ  NOT NULL,
    CONSTRAINT uk_identities_tenant_username UNIQUE (tenant_id, username)
);
CREATE INDEX IF NOT EXISTS ix_identities_tenant ON identities(tenant_id);

CREATE TABLE IF NOT EXISTS credentials (
    id             UUID PRIMARY KEY,
    identity_id    UUID NOT NULL UNIQUE REFERENCES identities(id) ON DELETE CASCADE,
    password_hash  VARCHAR(256) NOT NULL,
    algorithm      VARCHAR(32)  NOT NULL,
    updated_at     TIMESTAMPTZ  NOT NULL
);

CREATE TABLE IF NOT EXISTS mfa_factors (
    id          UUID PRIMARY KEY,
    identity_id UUID NOT NULL REFERENCES identities(id) ON DELETE CASCADE,
    type        VARCHAR(16) NOT NULL,
    secret      VARCHAR(256) NOT NULL,
    enabled     BOOLEAN NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_mfa_identity ON mfa_factors(identity_id);

CREATE TABLE identity_groups (
    identity_id UUID         NOT NULL REFERENCES identities(id) ON DELETE CASCADE,
    group_id    VARCHAR(100) NOT NULL,
    CONSTRAINT pk_identity_groups PRIMARY KEY (identity_id, group_id)
);

CREATE INDEX idx_identities_org        ON identities (organization_id);
CREATE INDEX idx_identities_email      ON identities (email);
CREATE INDEX idx_identities_status     ON identities (status);
CREATE INDEX idx_identities_org_status ON identities (organization_id, status);
CREATE INDEX idx_identities_federation ON identities (federation_provider, federation_external_id) WHERE federation_provider IS NOT NULL;
CREATE INDEX idx_identities_attributes ON identities USING GIN (attributes);

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$ BEGIN NEW.updated_at = now(); RETURN NEW; END; $$ LANGUAGE plpgsql;

CREATE TRIGGER trg_identities_updated_at
    BEFORE UPDATE ON identities
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
