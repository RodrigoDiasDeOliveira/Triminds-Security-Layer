
CREATE TABLE IF NOT EXISTS audit_event (
    id              UUID PRIMARY KEY,
    occurred_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    actor_id        VARCHAR(128),
    tenant_id       VARCHAR(128),
    action          VARCHAR(128) NOT NULL,
    resource_type   VARCHAR(128),
    resource_id     VARCHAR(256),
    outcome         VARCHAR(32)  NOT NULL,
    ip              VARCHAR(64),
    user_agent      TEXT,
    correlation_id  VARCHAR(128),
    payload         JSONB
);

CREATE INDEX IF NOT EXISTS idx_audit_event_actor      ON audit_event (actor_id);
CREATE INDEX IF NOT EXISTS idx_audit_event_action     ON audit_event (action);
CREATE INDEX IF NOT EXISTS idx_audit_event_occurred   ON audit_event (occurred_at DESC);
CREATE INDEX IF NOT EXISTS idx_audit_event_tenant     ON audit_event (tenant_id);
