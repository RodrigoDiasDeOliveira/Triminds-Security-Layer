# ADR-0006: Package Boundaries

## Status
Proposed

## Context
The monorepo includes service modules, a shared module, a frontend app and SDK packages. Clear boundaries are required to keep the architecture manageable.

## Decision
Establish the following package boundaries:

- `shared` contains only cross-cutting models, exceptions and utilities
- `security-*` modules implement individual backend services and their REST endpoints
- `admin-console` remains a frontend-only React application
- `sdks` hosts language-specific integration clients and examples

No service module should depend on `admin-console`, and frontend logic should not be placed in backend modules.

## Consequences
- clean separation of backend and frontend concerns
- easier packaging and release of individual modules
- lower risk of inter-module dependency issues
