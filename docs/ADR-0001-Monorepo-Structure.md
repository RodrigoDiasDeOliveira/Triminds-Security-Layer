# ADR-0001: Monorepo Structure

**Status**: Accepted  
**Date**: 2026-06-08

## Context

We need to develop multiple security modules that share models, utilities, and configurations.

## Decision

We will use a **Maven Multi-Module** monorepo with the following structure:
- `shared/` for common models and utilities
- Individual modules for each security domain
- `admin-console/` as a separate React application

## Rationale

- Better code reuse
- Single version control
- Easier dependency management
- Clear domain boundaries

## Consequences

- Larger repository size
- More complex build configuration
- Requires discipline in module boundaries
