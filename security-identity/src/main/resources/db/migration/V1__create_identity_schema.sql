CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE identities (
    id                      UUID            NOT NULL DEFAULT gen_random_uuid(),
    organization_id         UUID            NOT NULL,
    username                VARCHAR(100)    NOT NULL,
    email                   VARCHAR(255)    NOT NULL,
    display_name            VARCHAR(200),
    type                    VARCHAR(30)     NOT NULL,
    status                  VARCHAR(30)     NOT NULL DEFAULT 'PENDING_ACTIVATION',
    attributes              JSONB           NOT NULL DEFAULT '{}',
    federation_provider     VARCHAR(50),
    federation_external_id  VARCHAR(500),
    federation_issuer       VARCHAR(500),
    federation_subject      VARCHAR(500),
    failed_login_attempts   INTEGER         NOT NULL DEFAULT 0,
    locked_until            TIMESTAMPTZ,
    last_login_at           TIMESTAMPTZ,
    created_at              TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at              TIMESTAMPTZ     NOT NULL DEFAULT now(),
    version                 BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_identities PRIMARY KEY (id),
    CONSTRAINT uq_identities_username UNIQUE (username),
    CONSTRAINT chk_identities_type CHECK (type IN ('LOCAL_USER','FEDERATED_USER','SERVICE_ACCOUNT','APPLICATION')),
    CONSTRAINT chk_identities_status CHECK (status IN ('PENDING_ACTIVATION','ACTIVE','LOCKED','DISABLED'))
);

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
