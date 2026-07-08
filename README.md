
<img width="1168" height="784" alt="JWyiT" src="https://github.com/user-attachments/assets/94b5d400-9305-4dcd-9f4c-69d019ce2a3a" />

# Triminds Security Layer
# Triminds Security Layer

[![Java](https://img.shields.io/badge/Java-21-blue.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg)]()
[![License](https://img.shields.io/badge/license-MIT-green.svg)]()

Enterprise-grade Security Platform built with Java, Spring Boot and Hexagonal Architecture.



## Overview

Triminds Security Layer is a modular enterprise security platform designed to centralize authentication, authorization, policy evaluation, risk analysis, auditing and security intelligence.

The project follows modern software architecture principles, allowing each security capability to evolve independently while maintaining a clean separation between business rules and infrastructure.



## Architecture

The project combines multiple architectural patterns commonly used in enterprise software.

### Architectural Principles

- Hexagonal Architecture (Ports & Adapters)
- Clean Architecture
- Domain-Driven Design (DDD)
- Modular Monolith
- Event-Driven Architecture
- Zero Trust Security
- Policy-Based Access Control (PBAC)
- Multi-Tenant
- Cloud-Native Ready

Business rules remain independent from infrastructure concerns, making the platform easier to maintain, test and evolve.



## High-Level Architecture


                         Client Applications
                                 │
                                 ▼
                        Security Gateway
                                 │
         ┌───────────────────────┼────────────────────────┐
         ▼                       ▼                        ▼
 Identity Service         Authentication         Access Control
                                 │
                                 ▼
                        Policy Engine (OPA)
                                 │
                                 ▼
                          Risk Engine
                                 │
                 ┌───────────────┴───────────────┐
                 ▼                               ▼
          Audit Service             Intelligence Service



## Module Structure

triminds-security-layer
│
├── shared
│
├── security-identity
│
├── security-auth
│
├── security-access-control
│
├── security-policy-engine
│
├── security-risk-engine
│
├── security-gateway
│
├── security-intelligence
│
└── security-audit



## Responsibilities

| Module | Responsibility |
|---------|----------------|
| shared | Shared contracts, utilities and common infrastructure |
| security-identity | Identity lifecycle management |
| security-auth | Authentication and JWT management |
| security-access-control | Role and permission management |
| security-policy-engine | Policy evaluation using Open Policy Agent |
| security-risk-engine | Risk scoring and adaptive security |
| security-gateway | API Gateway and request propagation |
| security-intelligence | Security telemetry and analytics |
| security-audit | Audit trail and compliance |



## Technology Stack

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Spring Cloud
- PostgreSQL
- Redis
- Kafka
- Open Policy Agent (OPA)
- JWT
- OAuth2 Resource Server
- Docker
- Maven
- Micrometer
- Prometheus
- OpenTelemetry


## Design Goals

- High cohesion
- Low coupling
- Testability
- Cloud-native deployment
- Multi-tenant support
- Horizontal scalability
- Security by design
- Extensibility


## Project Status

Current stage:

- ✅ Core architecture completed
- ✅ Modular structure stabilized
- ✅ All modules compiling successfully
- 🚧 Integration tests
- 🚧 Docker Compose environment
- 🚧 Kubernetes deployment
- 🚧 CI/CD pipeline


## Roadmap

- Complete integration testing
- Automated CI/CD
- Kubernetes deployment
- Observability improvements
- Security dashboards
- AI-assisted anomaly detection


## License

MIT License
