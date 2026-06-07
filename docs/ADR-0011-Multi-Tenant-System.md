# ADR-0011: Multi-Tenant System

## Status
Proposed

## Context
Even though the current project is an internal Triminds platform, the architecture must support tenant separation for business units or customer teams.

## Decision
Design for multi-tenant support with:

- tenant-aware user authentication and authorization
- isolated policy, risk and audit data per tenant
- optional tenant branding and UI configuration
- shared infrastructure with strict tenant boundaries

## Consequences
- enables reuse of the platform across multiple tenants
- increases the need for tenant isolation in data and APIs
- supports future business expansion without separate deployments
