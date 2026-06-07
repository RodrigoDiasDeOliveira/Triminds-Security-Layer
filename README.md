# Triminds Security Layer

A smart security framework for distributed systems at Triminds.

## Overview

This repository aims to deliver a reusable security platform for Triminds applications, built as a modular Identity & Security Platform.

### Four pillars

1. Identity and Access Management (IAM)
2. Application and API Security
3. Risk Monitoring and Detection
4. Governance and Compliance

## Monorepo structure

- `security-identity/` - Identity Provider
- `security-auth/` - Authentication, tokens, MFA, SSO
- `security-access-control/` - RBAC, ABAC, access policies
- `security-policy-engine/` - Deterministic policy engine
- `security-risk-engine/` - Risk engine with AI/ML
- `security-gateway/` - API enforcement gateway
- `security-intelligence/` - Observability and anomaly detection
- `security-audit/` - Immutable audit trails and compliance
- `admin-console/` - Management UI (React + Triminds UI)
- `sdks/` - SDKs for Java, TypeScript, Python, Go
- `shared/` - Shared models, events, and utilities
- `docs/` - Architecture and ADR documentation

## Initial technology stack

- Java 21
- Spring Boot 3.4
- Maven
- PostgreSQL + Redis
- Spring Authorization Server
- OpenTelemetry + Grafana
- OPA (Open Policy Agent)
- LangChain4j

## Getting started

1. `mvn -pl shared,security-identity,... -am clean install`
2. Start each service as a Spring Boot application
3. Develop the `admin-console` separately in React

> This is the initial product structure. The next phase is to implement each module with real APIs and service integration.
