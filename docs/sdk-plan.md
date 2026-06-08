# Multi-Language SDK Plan

## Overview

SDKs are key components to make Triminds Security Layer easy to adopt across different ecosystems.

### Primary Objectives

- Validate tokens and manage sessions consistently
- Offer simple integrations with Security Gateway
- Standardize audit calls, logs, and risk events
- Support dynamic policies and adaptive authorization

## Rollout Strategy

1. `typescript/` as the reference SDK for front-end and Node.js
2. `java/` for enterprise clients and Spring Boot integration
3. `python/` for automation, data science, and serverless services
4. `go/` for infrastructure, agents, and low-latency services

## Essential Modules

- Auth client
- Gateway client
- Policy client
- Audit client
- Common models / DTOs

## Expected Patterns and APIs

- JWT and OAuth2 support
- Secure connections via HTTPS
- Integration with OPA for policy decisions
- Endpoint documentation and usage examples
