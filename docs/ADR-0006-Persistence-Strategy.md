# ADR-0006: Persistence Strategy

**Status**: Accepted  
**Date**: 2026-06-08

## Context

Different platform components have different storage requirements:

- Identity data
- Security policies
- Audit logs
- Threat intelligence
- AI analysis
- Session management

No single database technology adequately addresses all requirements.

## Decision

Adopt a polyglot persistence strategy.

## Storage Technologies

### PostgreSQL

Used for:

- Users
- Roles
- Permissions
- Applications
- Tenant Configuration

### Redis

Used for:

- Session Storage
- Token Blacklists
- MFA Challenges
- Temporary Security Context

### Vector Database

Used for:

- Threat Intelligence
- Security Knowledge Base
- AI Context Retrieval
- Semantic Search

### Object Storage

Used for:

- Reports
- Audit Exports
- Evidence Files

## Rationale

- Best tool for each workload
- Improved performance
- Better scalability
- Future AI readiness

## Consequences

### Positive

- Optimized storage architecture
- Better system performance

### Negative

- More operational complexity
- Multiple backup strategies required

## Status

PostgreSQL remains the system of record.
