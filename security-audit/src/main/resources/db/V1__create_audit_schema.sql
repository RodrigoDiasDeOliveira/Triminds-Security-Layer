CREATE TABLE audit_events (
    id           UUID PRIMARY KEY,
    tenant_id    UUID NOT NULL,
    type         VARCHAR(80) NOT NULL,
    occurred_at  TIMESTAMP NOT NULL,
    payload_json TEXT NOT NULL,
    prev_hash    VARCHAR(80) NOT NULL,
    entry_hash   VARCHAR(80) NOT NULL,
    sequence     BIGINT NOT NULL,
    CONSTRAINT uk_audit_tenant_seq UNIQUE (tenant_id, sequence)
);
CREATE INDEX idx_audit_tenant_seq  ON audit_events(tenant_id, sequence);
CREATE INDEX idx_audit_tenant_when ON audit_events(tenant_id, occurred_at);
CREATE INDEX idx_audit_type        ON audit_events(type);
