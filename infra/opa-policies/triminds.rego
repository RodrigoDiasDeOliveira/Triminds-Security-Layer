# Política Rego default da Triminds.
# Carregada em OPA via /v1/policies/triminds.allow

package triminds

default allow := false

# permite quando RBAC já validou (input vem do policy-engine)
allow if {
    input.attributes.role == "admin"
}

allow if {
    input.action == "read"
    startswith(input.resource, "/api/audit/")
    input.attributes.department == "security"
}
