CREATE TABLE roles (
    id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE permissions (
    id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(150) NOT NULL UNIQUE,
    resource    VARCHAR(100),
    action      VARCHAR(50),
    description TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE role_permissions (
    role_id       UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE user_roles (
    identity_id UUID NOT NULL,
    role_id     UUID NOT NULL REFERENCES roles(id),
    organization_id UUID NOT NULL,
    PRIMARY KEY (identity_id, role_id, organization_id)
);

CREATE INDEX idx_roles_name ON roles(name);
CREATE INDEX idx_permissions_name ON permissions(name);
