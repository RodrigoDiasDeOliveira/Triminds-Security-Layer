# ADR-0002: Identity Provider - Spring Authorization Server

**Status**: Accepted  
**Date**: 2026-06-08

## Context

We need a robust, standards-compliant Identity Provider supporting OAuth2 and OpenID Connect.

## Decision

Use **Spring Authorization Server** (official Spring project) as our main Identity Provider.

## Rationale

- Native integration with Spring Boot
- Full OAuth2 / OIDC support
- Good customization capabilities
- Active community and maintenance by Spring team

## Alternatives Considered

- Keycloak (rejected - too heavy for our needs)
- Ory Kratos/Hydra (rejected - added complexity)

## Supported Standards

- OAuth2 Authorization Framework
- OpenID Connect 1.0
- JWT
- PKCE
- OAuth2 Client Credentials
- OAuth2 Authorization Code Flow