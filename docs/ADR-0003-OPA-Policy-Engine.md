# ADR-0003: Policy Engine - Open Policy Agent (OPA)

**Status**: Accepted  
**Date**: 2026-06-08

## Context

We need a centralized, decoupled, and high-performance policy decision engine.

## Decision

Adopt **Open Policy Agent (OPA)** + Rego language for policy definition.

## Policy Model

The platform will support:

- RBAC
- ABAC
- Contextual Policies
- Risk-Based Policies

## Rationale

- Decouples policy from code
- High performance
- Excellent for fine-grained authorization (ABAC)
- GitOps friendly

## Status

Policy Engine module will expose a REST API that queries OPA.
