# ADR-0005: Event-Driven Architecture

**Status**: Accepted  
**Date**: 2026-06-08

## Context

Security events are generated continuously by multiple modules:

- Authentication Service
- Identity Provider
- Access Control
- Policy Engine
- Risk Engine
- Audit Service
- Threat Intelligence

A synchronous architecture would create tight coupling between services and reduce scalability.

## Decision

Adopt an **Event-Driven Architecture** using Apache Kafka as the central event backbone.

## Event Categories

- Authentication Events
- Authorization Events
- Risk Events
- Audit Events
- Threat Intelligence Events
- User Lifecycle Events

## Rationale

- Loose coupling between modules
- Better scalability
- Real-time processing capabilities
- Easier integration of future modules
- Improved resiliency

## Consequences

### Positive

- Near real-time event propagation
- Independent service evolution
- Better observability

### Negative

- Increased operational complexity
- Event schema governance required
- Eventual consistency challenges

## Status

All modules must publish domain events through Kafka topics.
