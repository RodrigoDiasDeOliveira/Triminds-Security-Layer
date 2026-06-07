# ADR-0001: Monorepo + Design System

## Status
Proposed

## Context
The Triminds Security Layer has been structured as a Maven monorepo with independent Java service modules and one React-based admin console. A shared module holds cross-cutting models and utilities.

## Decision
Adopt a multi-module Maven repo with the following components:

- `shared` for common domain models and utilities
- `security-identity` for identity provider services
- `security-auth` for authentication and token handling
- `security-access-control` for RBAC/ABAC logic
- `security-policy-engine` for dynamic policy evaluation
- `security-risk-engine` for risk scoring and anomaly orchestration
- `security-gateway` for API enforcement
- `security-intelligence` for observability endpoints
- `security-audit` for audit trails
- `admin-console` for the React/Vite management UI
- `sdks` for multi-language client SDKs

The Admin Console will evolve toward a shared design system, starting with a consistent AppShell, theme, and common page patterns.

## Consequences
- Better dependency control and modular build behavior
- Shared models can be reused without duplicating domain definitions
- Frontend can progressively adopt a Triminds design system
