CREATE TABLE roles (

id UUID PRIMARY KEY,

tenant_id UUID NOT NULL,

name VARCHAR(100) NOT NULL,

description TEXT,

created_at TIMESTAMP NOT NULL

);



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