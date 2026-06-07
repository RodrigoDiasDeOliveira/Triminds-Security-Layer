# ADR-0008: Data Fetching Layer

## Status
Proposed

## Context
The admin console is currently a static scaffold without a shared data access layer. Future work should standardize API communication across the UI and SDKs.

## Decision
Create a shared data fetching layer that includes:

- a reusable HTTP client wrapper for backend API calls
- authentication token management and request interceptors
- retry and error handling policies
- optional caching and stale-while-revalidate support

This layer should eventually be usable by both `admin-console` and the generated SDKs.

## Consequences
- reduces duplicated network code
- centralizes auth and error handling
- provides a consistent integration model for services
