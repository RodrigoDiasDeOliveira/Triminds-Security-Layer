-- Triminds access-control schema

CREATE TABLE roles (
    id          UUID PRIMARY KEY,
    tenant_id   UUID NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_roles_tenant_name UNIQUE (tenant_id, name)
);
CREATE INDEX idx_roles_tenant ON roles(tenant_id);

CREATE TABLE permissions (
    id       UUID PRIMARY KEY,
    action   VARCHAR(100) NOT NULL,
    resource VARCHAR(255) NOT NULL,
    CONSTRAINT uk_permissions_action_resource UNIQUE (action, resource)
);

CREATE TABLE role_permissions (
    role_id       UUID NOT NULL REFERENCES roles(id)       ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE identity_roles (
    id          UUID PRIMARY KEY,
    tenant_id   UUID NOT NULL,
    identity_id UUID NOT NULL,
    role_id     UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    created_at  TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_identity_roles UNIQUE (tenant_id, identity_id, role_id)
);
CREATE INDEX idx_identity_roles_identity ON identity_roles(tenant_id, identity_id);

CREATE TABLE abac_attributes (
    id              UUID PRIMARY KEY,
    tenant_id       UUID NOT NULL,
    identity_id     UUID NOT NULL,
    attribute_name  VARCHAR(100),
    attribute_value VARCHAR(255)
);
CREATE INDEX idx_abac_identity ON abac_attributes(tenant_id, identity_id);


CREATE TABLE permissions (

id UUID PRIMARY KEY,

action VARCHAR(100) NOT NULL,

resource VARCHAR(255) NOT NULL

);



CREATE TABLE role_permissions (

role_id UUID NOT NULL,

permission_id UUID NOT NULL,


PRIMARY KEY(role_id,permission_id)

);



CREATE TABLE identity_roles (

id UUID PRIMARY KEY,

tenant_id UUID NOT NULL,

identity_id UUID NOT NULL,

role_id UUID NOT NULL,

created_at TIMESTAMP NOT NULL

);



CREATE TABLE abac_attributes (

id UUID PRIMARY KEY,

tenant_id UUID NOT NULL,

identity_id UUID NOT NULL,

attribute_name VARCHAR(100),

attribute_value VARCHAR(255)

);



CREATE INDEX idx_roles_tenant

ON roles(tenant_id);



CREATE INDEX idx_identity_roles_identity

ON identity_roles(identity_id);